ActiveDiscounts
===============

First version of the Active Discounts project.

1.0 How to Install and Run:
=======================

Pre-Install Steps:
------------------
a. Install JDK 1.6
b. Install Tomcat 6.0
c. Install MySql and set username as 'root' and password as 'Passw0rd'
d. Create database called 'ads' as 'utf8_general_ci'
e. Install IntelliJ IDEA
f. Install Maven and configure this in IntelliJ

Project Setup:
---------------
a. From IntelliJ IDE, Checkout the project with git option
b. Build the whole project using Maven tool from IntelliJ
c. Configure Site Build.xml file from Ant
d. Run create-sql from site Ant tasks
e. Run update-sql from site Ant tasks
f. Go to Run Configuration and select 'site' as Maven Module
   Pass the following JVM parameters.   
   
-Xmx1296m
-Xms512m
-XX:PermSize=1048m
-XX:MaxPermSize=1096m
-Ddatabase.user=root
-Ddatabase.password=Passw0rd
-Ddatabase.driver=com.mysql.jdbc.Driver
-Ddatabase.url=jdbc:mysql://localhost:3306/ads?useUnicode=true&amp;characterEncoding=utf-8
-Dhibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
-Druntime.environment=development

g. You will see the tables and data populated in the database 'ads' after you deployed and started tomcat with 'site'
f. Now, Configure admin from 'Run Configurations' but this time as Tomcat-->local.
   Select admin.war and add the below JVM Parameters. You can optionally edit tomcat port by changing the value of the paramter
   maven.tomcat.port below.
   
-Xmx1296m
-Xms512m
-XX:PermSize=1048m
-XX:MaxPermSize=1096m
-Dmaven.tomcat.port=8181
-Ddatabase.user=root
-Ddatabase.password=Passw0rd
-Ddatabase.driver=com.mysql.jdbc.Driver
-Ddatabase.url=jdbc:mysql://localhost:3306/ads?useUnicode=true&amp;characterEncoding=utf-8
-Dhibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
-Druntime.environment=development

h. Change environment by assigning correct value above (runtime.environment) which will take the properties from the respective files.


2.0 Runtime Environment Configuration:
==================================

Class: RuntimeEnvironmentPropertiesConfigurer.java

Property override Order:
------------------------
a. Properties defined in the file specified by runtime JVM argument "property-override"
b. Properties defined in the file specified by runtime JVM argument "property-shared-override"

   eg: -Dproperty-share-override=/Users/SomeUser/SomeProject/secret.properties

c. development.properties from the specific application (either site or admin)
d. common.properties from the specific application (either site or admin)
e. development-shared.properties in the core project
f. common-shared.properties in the core project
g. Broadleaf defined properties

Configuration:
--------------

Add 'blConfiguration' in 'applicationContext.xml' and 'applicationContext-admin.xml' like below.
<bean id="blConfiguration" class="org.broadleafcommerce.common.config.RuntimeEnvironmentPropertiesConfigurer" />

And pass this JVM Parameter for the correct environment.
eg: -Druntime.environment=production


CMS Config:
-----------
a. Add Path to store Assets (asset.server.file.system.path)
























    
    
    
    
    
    
    

   
   
