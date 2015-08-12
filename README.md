# MIPr - MapReduce Image Processing framework for Hadoop

MIPr provides the ability to process images in Hadoop.

MIPr includes:

* Writable Wrappers for images
* InputFormat and OutputFormat for images
* Several Jobs for image processing
* OpenCV and OpenIMAJ support

## Installation

### Prerequisites

* Java 7 (preferably Oracle)
* Maven 3.2.5

### Building

1. Clone repository with MIPr sources

    `git clone ...`

2. Build package by using Apache Maven

    To build full package with OpenIMAJ and OpenCV support run

    `mvn package`

    Notice that size of the package will be greater than separate build

    To build separate packages run

    `mvn package -pl [desired_package] -am`

    Where **desired_package** is one of the followings:

    - core_package
    - includes_OpenCV (includes core with OpenCV support)
    - includes_OpenIMAJ (includes core with OpenIMAJ support)

3. It will build jar file *...-jar-with-dependencies.jar* and place it in the *target* folder.

### Running

1. Copy image files to HDFS:

    `$ hadoop fs -copyFromLocal local_image_folder hdfs_image_folder`

2. Run test MIPr Job which converts color images to grayscale:

    `$ hadoop jar mipr-core-0.1-jar-with-dependencies.jar experiments.Img2Gray hdfs_image_folder hdfs_output_folder`

3. Copy processed images back from HDFS to the local filesystem:

    `$ hadoop fs -copyToLocal hdfs_output_folder local_output_folder`

4. Check that images were converted correctly.

### Creating your own Hadoop job

To process images by your own way you need to create 1 class. For example, lets create job, which processes color images to grayscale.
For now, MIPr already has this class wihch placed in *core_package\src\main\java\experiments*.

1. Create public class inhereted from **Configured** superclass and **Tool** interface.

    ```java
    public class Img2Gray extends Configured implements Tool{
        public static void main(String[] args) throws Exception {
            int res  = ToolRunner.run(new Img2Gray(), args);
            System.exit(res);
        }
    ```