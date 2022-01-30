# ApplyServer

#### ApplyServer is a java websocket based server, which lets users apply to your company or project. The team members can then review the applications and give feedback. 

The program is currently under development and is not finished yet!

## Todo List

- [ ] Improve Home page design and functionality
- [ ] Account Manager (all accounts listed, add users to team)
- [ ] Page for team to create application formula
- [ ] Update Apply page

## Feature List

- [x] Create account function
- [x] Login Page
- [x] Secured home page (can't access without logging in before)
- [x] Config file for server settings
- [x] Logs function (file where every connection etc. is listed)
- [x] Customizable webdesign (access to all html etc. files)
- [x] Saving and getting data from JSON files
- [x] Apply page with form  
- [x] Encrypted password saving

## Downloads:

**Warning these are currently only pre-releases and may have some problems or malfunctions!**
[Releases](https://github.com/BytePhilHD/ApplyServer/releases)

<h1>How to install</h1>
Download the newest Version from the Releases Page.

**IMPORTANT: Install Java 11, if you havent already**

**Windows:** Create a batch file (e.g. "start.bat") in that you need the Line 
`java -Xmx1G -jar ApplyServer.jar`. If you rename your .jar file, type your new Name
instead of "WebTest". 

**Linux:** Create a sh file (e.g. "start.sh") in that you need the Line
`screen -AmdS ApplyServer java -Xmx1G -jar ApplyServer.jar`. If you havent already we recommend
installing screen (apt install screen), if you dont want to use it, just write the same as on Windows.

Now start the file (Windows doubleclick, Linux ./start.sh). The Programm should start up. (On linux you may need to chmod the file `chmod 777 start.sh`)
If you're running the Programm at home, you may need to release your Port (standard 80) in your
Routers setting.

Now you can access your Site via localhost (if you changed your Port localhost:YOURPORT) or
use the IP Address instead of localhost

## Contributors:
@BytePhilHD
@Chrisimx


