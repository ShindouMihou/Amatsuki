[![](https://jitpack.io/v/ShindouMihou/amatsuki.svg)](https://jitpack.io/#ShindouMihou/amatsuki)
# Amatsuki

Amatsuki is an asychronous simple text scraper for ScribbleHub built in Java, the main purpose for this library is to scrape public user data (bio, followed by x users, etc...) , or story data (synopsis, story title, thumbnail, etc...) from the https://scribblehub.com site.

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

Amatsuki has a single class that has everything needed, this class is called ``Amatsuki`` and all the methods are named after their use which should simplifies everything.
If you also want to, you can improve everything to your liking as I will only be using this for the basic tasks.

Please note, that all if not most of these methods returns either an CompletableFuture or an Optional, if you do not know how to use them, please search around the web for some guides. \w/

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

#### To collect data about the current trending at the time of execution.
```java
new Amatsuki().getTrending(); // Gets the trending (daily).
new Amatsuki().getTrending(OrderBy.WEEKLY); // Gets the weekly trending.
```

#### To collect data about the current latest series or latest updates at the time of execution.
```java
new Amatsuki().getLatestSeries(); // latest series.
new Amatsuki().getLatestUpdates(); // latest updates.
```

#### To collect data about a certain ranking, simply use.
```java
new Amatsuki().getCertainRankings();
```
###### Example Usage:
```java
new Amatsuki().getCertainRankings(Rankings.ACTIVITY, OrderBy.WEEKLY); // This will get the Activity Rankings, ordered by weekly.
new Amatsuki().getCertainRankings(Rankings.ACTIVITY); // Exactly the same as the first one except this uses the default order (Daily).
```

#### You can also specify a timeout if you wish, by default, the timeout is 5 seconds.
```java
new Amatsuki().searchUser("Mihou", 30000); // Timeout is in millis.
```

#### Known issues
Here are the currently known issues, if anyone knows the fix, feel free to send a PR.

1. IOException: Underlying Input Stream Returned Zero Bytes. [haven't occurred as of lately]
