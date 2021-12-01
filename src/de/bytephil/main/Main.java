package de.bytephil.main;

import de.bytephil.enums.MessageType;
import de.bytephil.utils.*;
import de.bytephil.utils.Console;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main {

    private static Javalin app;
    private static java.lang.Thread thread;

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }
    public Main() {
        instance = this;
    }

    public static String password;
    private static ArrayList<String> clients = new ArrayList<>();
    private static ArrayList<String> logtIn = new ArrayList<>();

    private ServerConfiguration config;
    public String version = "0.0.2";
    public boolean debugMSG = false;

    public void startUp() throws IOException, URISyntaxException {
        new FileService().copyDirectoryfromResources("resources/public", "Webpages");
        new AccountManager().addUser("Phil", "BytePhil", "mail@mail", "password!");
        new LogService().logFileCreation();
        checkandCreateFile("data/Applications.txt", "data");
        new LogService().writetoFile(new File("logs/log.txt"), "The program is trying to start...", MessageType.INFO);
        //ApplicationService.addnew("BytePhil", "BytePhil#9293");

        if (!new File("server.cfg").exists()) {
            de.bytephil.utils.Console.printout("The config file is missing! Creating default one.", MessageType.WARNING);
            final File newFile = new File("server.cfg");
            copyFile(newFile, "resources/default.cfg");
        }

        // Load config
        config = new ServerConfiguration("server.cfg");
        if (config.loaded) {
            Console.printout("Config was successfully loaded!", MessageType.INFO);
            password = config.password;
            debugMSG = config.debugMSG;
        } else {
            Console.printout("Config not loaded! Using default.", MessageType.WARNING);
        }
        //MySQLService.startMySQL();
        startApp();
    }


    public void startApp() throws IOException {

        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("WebPages", Location.EXTERNAL);
            config.server(() -> {
                Server server = new Server();
                ArrayList<Connector> connectors = new ArrayList<>();
                if (this.config.https) {
                    ServerConnector sslConnector = new ServerConnector(server, getSslContextFactory());
                    sslConnector.setPort(this.config.sslPort);
                    connectors.add(sslConnector);
                }
                if (this.config.http) {
                    ServerConnector connector = new ServerConnector(server);
                    connector.setPort(this.config.port);
                    connectors.add(connector);
                }
                server.setConnectors(connectors.toArray(new Connector[0]));
                return server;
            });
        }).start();

        new LogService().writetoFile(new File("logs/log.txt"), "The program successfully started!", MessageType.INFO);
        if (debugMSG) {
            Console.printout("Debug Messages Enabled! To turn off, change \"debugMSG=true\" to \"debugMSG=false\" in your server.cfg!", MessageType.WARNING);
        }


        Main.app = app;
       // thread = UpdateThread.thread;
        // thread.start();

        app.ws("/checklogin", ws -> {
            ws.onConnect(ctx -> {
                Console.printout("[/websockets] Client connected with Session-ID: " + ctx.getSessionId() + " IP: " + ctx.session.getRemoteAddress(), MessageType.DEBUG);
                new LogService().writetoFile(new File("logs/log.txt"), "Client connected with Session-ID: " + ctx.getSessionId() + " IP: " + ctx.session.getRemoteAddress(), MessageType.INFO);
                clients.add(ctx.getSessionId());
            });
            ws.onClose(ctx -> {
                Console.printout("[/websockets] Client disconnected (Session-ID: " + ctx.getSessionId() + ")", MessageType.DEBUG);
                clients.remove(ctx.getSessionId());
            });
            ws.onMessage(ctx -> {
                String message = ctx.message();

                if (message.contains("LOGIN")) {
                    message = message.replace("LOGIN: ", "");
                    if (Login.login(message)) {
                        ctx.send("CORRECT " + ctx.getSessionId());
                        logtIn.add(ctx.getSessionId());
                        Console.printout("User logged in successfully (Session-ID: " + ctx.getSessionId() + ", IP: " + ctx.session.getRemoteAddress(), MessageType.INFO);
                        new LogService().writetoFile(new File("logs/log.txt"), "Client logged in successfully with IP: " + ctx.session.getRemoteAddress(), MessageType.INFO);
                    } else {
                        ctx.send("WRONG");
                    }
                }
            });
        });

        app.ws("/login", ws -> {
            ws.onConnect(ctx -> {
                Console.printout("[/login] Client connected with Session-ID: " + ctx.getSessionId() + " IP: " + ctx.session.getRemoteAddress(), MessageType.DEBUG);
            });
            ws.onClose(ctx -> {
                Console.printout("[/login] Client disconnected (Session-ID: " + ctx.getSessionId() + ")", MessageType.DEBUG);
            });
            ws.onMessage(ctx -> {
                String message = ctx.message();
                if (message.contains("LOGIN")) {
                    message = message.replace("LOGIN: ", "").replace("?", "");

                    if (logtIn.contains(message)) {
                        logtIn.add(ctx.getSessionId());
                        logtIn.remove(message);
                    } else {
                        ctx.send("CLOSE");
                        new LogService().writetoFile(new File("logs/log.txt"), "Client disconnected with IP: " + ctx.session.getRemoteAddress(), MessageType.INFO);
                    }
                } else if (message.contains("FILE")) {
                    String url = message.replace("FILE: ", "").replace("blob:", "");
                    System.out.println(url);
                }
            });
            ws.onError(ctx -> {
                Console.printout(ctx.error().toString(), MessageType.ERROR);
            });
        });
        app.get("/login", ctx -> {
            ctx.render("/public/login.html");
        });
        app.get("/home", ctx -> {
            ctx.render("/public/home.html");
        });
    }

    public void copyFile(File newFile, String existingFile) throws IOException {
        newFile.createNewFile();
        final FileOutputStream configOutputStream = new FileOutputStream(newFile);
        byte[] buffer = new byte[4096];
        final InputStream defaultConfStream = getClass().getClassLoader().getResourceAsStream(existingFile);
        int readBytes;
        while ((readBytes = defaultConfStream.read(buffer)) > 0) {
            configOutputStream.write(buffer, 0, readBytes);
        }
        defaultConfStream.close();
    }

    public static void checkandCreateFile(String path, String folder) throws IOException {
        if (!new File(path).exists()) {
            File dir = new File(folder);
            if (!dir.exists()) dir.mkdirs();
            final File newFile = new File(path);
            newFile.createNewFile();
        }
    }

    private SslContextFactory getSslContextFactory() {
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(config.keystorePath);
        sslContextFactory.setKeyStorePassword(config.keystorePW);
        return sslContextFactory;
    }
}
