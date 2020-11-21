[![](https://jitpack.io/v/ShindouMihou/amatsuki.svg)](https://jitpack.io/#ShindouMihou/amatsuki)
# Amatsuki

Amatsuki is an asychronous simple text scraper for ScribbleHub built in Java, the main purpose for this library is to scrape public user data (bio, followed by x users, etc...) , or story data (synopsis, story title, thumbnail, etc...). The library is compatiable with any Java version above 1.8 (standard), and heavily uses on CompletableFutures and Optionals to achieve its results.

The main use for this library is to search for stories, or users in the website ScribbleHub, and is mostly implemented on Discord Bots instead of actual heavy load programs. Please keep in mind that this library is still a mess, but it achieves what it can. 

If you wish to improve the library, please feel free to do so.

## How to add.

As of now, the only way to get this is through Jitpack which is still a simple way.

### Maven
#### Step 1. Add the JitPack repository to your build file
```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  ```
#### Step 2. Add the dependency
```
	<dependency>
	    <groupId>com.github.ShindouMihou</groupId>
	    <artifactId>amatsuki</artifactId>
	    <version>Tag</version>
	</dependency>
  ```
  
### Gradle
#### Step 1. Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
#### Step 2. Add the dependency:
```
	dependencies {
	        implementation 'com.github.ShindouMihou:amatsuki:Tag'
	}
  ```
  
 ##### Tag refers to version.

## How to use?

Amatsuki has a single class that has everything needed, this class is called ``Amatsuki`` which is as expected.
If you also want to, you can improve everything to your liking as I will only be using this for the basic tasks.

All of these results in an ``CompletableFuture<Optional>``, Optionals are used to protect against null whilst CompletableFuture
is used for asynchronous tasks.

#### To search for a story (using keyword).
```java
new Amatsuki().searchStory("A Dream Foretold");
```

#### To search for a user (using keyword), returns CompletableFuture<Optional>.
```java
new Amatsuki().searchUser("Mihou");
```

#### To collect information about a user using URL.
```java
new Amatsuki().getUserFromUrl("https://www.scribblehub.com/profile/24680/mihou/");
```

#### To collect information about a story using URL.
```java
new Amatsuki().getStoryFromUrl("https://www.scribblehub.com/series/193852/a-dream-foretold/");
```

#### You can also specify a timeout if you wish, by default, the timeout is 5 seconds.
```java
new Amatsuki().searchUser("Mihou", 30000); // Timeout is in millis.
```

#### Known issues
Here are the currently known issues, if anyone knows the fix, feel free to send a PR.

1. IOException: Underlying Input Stream Returned Zero Bytes.
##### Occurrs in both 30 seconds and 5 second timeouts.
