[![](https://jitpack.io/v/ShindouMihou/amatsuki.svg)](https://jitpack.io/#ShindouMihou/amatsuki)
# Amatsuki

A simple scraper for ScribbleHub built in Java, for searching stories, users or collecting information of those two using either keywords, or URLs.
- Built in Java, meant for a Discord bot.

## How to add.

As of now, the only way to get this is through Jitpack which is still a simple way.
Step 1. Add the JitPack repository to your build file

- Maven
Step 1. Add the JitPack repository to your build file
```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  ```
Step 2. Add the dependency
```
	<dependency>
	    <groupId>com.github.ShindouMihou</groupId>
	    <artifactId>amatsuki</artifactId>
	    <version>Tag</version>
	</dependency>
  ```
  
- Gradle
Step 1. Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency:
```
	dependencies {
	        implementation 'com.github.ShindouMihou:amatsuki:Tag'
	}
  ```
  
 - Tag refers to version.

## How to use?

Amatsuki has a single class that has everything needed, this class is called ``Amatsuki`` which is as expected.
If you also want to, you can improve everything to your liking as I will only be using this for the basic tasks.

- To search for a story (using keyword), all the results here returns as an Optional.
```java
new Amatsuki().searchStory("A Dream Foretold");
```

- To search for a user (using keyword), returns Optional.
```java
new Amatsuki().searchUser("Mihou");
```

- To collect information about a user using URL, returns an Optional as we don't know if the URL may be broken :'(.
```java
new Amatsuki().getUserFromUrl("https://www.scribblehub.com/profile/24680/mihou/");
```

- To collect information about a story using URL, returns Optional for the same reason :'(.
```java
new Amatsuki().getStoryFromUrl("https://www.scribblehub.com/series/193852/a-dream-foretold/");
```

- You can also specify a timeout if you wish, by default, the timeout is 30 seconds.
```java
new Amatsuki().searchUser("Mihou", 30000); // Timeout is in millis.
```
