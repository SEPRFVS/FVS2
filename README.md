FVS2
====

Assessment 2 Project Code

Project Set-up Instructions
===========================

1. Clone the repository.  It helps.  Don't worry about your project files overwriting any other files, Git has been configured to ignore these.  If you're using the GitHub tools, also install good old fashioned Git as it integrated with both Eclipse and IntelliJ IDEA to make managing version control easier.
2. I (Manfred) suggest using Feature Branch Workflow when working with Git. Please read https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow and https://guides.github.com/introduction/flow/index.html before trying to push anything to our repo.
3. If you will be using eclipse, set your workspace to be the repository root.  You should also install the Gradle IDE from http://dist.springsource.com/snapshot/TOOLS/gradle/nightly.  You may also find the Git plug-in useful if it is not already installed.
4. In Eclipse go to File -> Import -> Gradle -> Gradle Project.  Choose the taxe folder in the repo and then press Build Model.  You should import both Core and Desktop, the taxe project is just a necessary wrapper and will never be used for anything.  Click Import and wait patiently.
5. To set up IntelliJ IDEA, clone the repository into IDEA projects folder (I used terminal for that). Now open up IDEA and select that you'll import a project from a file/folder. Select build.gradle file from FVS2/taxe/core folder. Now IDEA does some importing and downloading.
To setup run environment:
Click Run -> Edit configurations...
Click on + in upper left.
Select Application and use Desktop for the name.
Select ../FVS2/taxe/core/assets as the Working directory
Use classpath of module: desktop
Main class: uk.ac.york.cs.sepr.fvs.taxe.desktop.DesktopLauncher
And it's ready.
You can try clicking on Run now and you should see the sample program work :)
6. Setting up and using Git on IDEA is really intuitive.
