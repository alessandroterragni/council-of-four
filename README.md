# *Council of Four™* --- README #
*The img folder is empty due to copyright reasons, thus, the gui will not work*
### Introduction ###

##### Quick Summary #####

This repository is associated with the "Prova Finale" project for the "Ingegneria del Software" course at Politecnico di Milano, held by professors Carlo Ghezzi, Claudio Menghi and Srđan Krstić.

The project was designed and implemented by Alessandro Terragni, Mario Scrocca and Marco Trabucchi, during the second semester of A.Y. 2015-2016. The code of the work group is `cg_28`.

##### Project Subject #####

Subject of this project is a game application written in Java (SE8), based on the board game *Council of Four™*, designed and produced by *Cranio Creations™* in 2015.

Here are some links to the [*Cranio Creations™*](http://www.craniocreations.it/) official website and the page about the [*Council of Four™* game](http://www.craniocreations.it/prodotto/consiglio-dei-quattro/) itself.

### Setting things up ###

##### First Steps #####

The game does not come with a precompiled `.jar` file.

For this reason, in order to set up a game it is necessary to follow some steps:
*(Note: all of the following phases require Eclipse or an adequate equivalent IDE to be performed and for this game to run properly)*

1. **Running the local in-game server**

    This can be done by running the `ServerManager.java` class in the `connections` package. After this step, you should see a console message that reads something similar to this:

    > lug 02, 2016 11:10:56 AM it.polimi.ingsw.cg28.connections.server.ServerManager main

    > INFORMAZIONI: Server is on!

    If there is no error message, the in-game local server should be running properly.

2. **Alternative 1: Running the desired number of clients via CLI interface selection**

    This step can be easily done by running the `Client.java` class in the `connections` package; you have to repeat the process once for every player you want to add to the match.

    Running the `Client.java` class is not sufficient to enter a match. In any console corresponding to a client process, you will be prompted this way:

    > Enter your name:

    After entering the player's name, the console will state:

    > Enter GUI or CLI to choose game interface:

    You will need to write GUI to play with a *Graphical User Interface* or CLI to use a *Command Line Interface*, based on your preferences. Finally, the console will ask:

    > Enter RMI or SOCKET to choose connection type:

    Here you can choose to connect to the server using an RMI (*Remote Method Invocation*) paradigm or a SOCKET architecture paradigm. Both connection types can run flawlessly with either GUI or CLI, and simultaneously for the same match (e.g. two clients can participate in the same match and use different connection types, and they will cooperate without any issue).

3. **Alternative 2: Running the desired number of clients via GUI interface selection**

    You can choose to run the clients by running the `GuiClient.java` class in the `connections` package. You will be able to perform the same choices listed above interacting with a dedicated graphical interface instead of typing them into the command line. It should look something like this:

    ![Alt text](https://github.com/alessandroterragni/council-of-four/blob/master/src/img/readme/gui.png)

---------------------------------------

If you want to just play with the given configurations, these are the only steps required to do so. We remind you that the existing configurations represent some of the original game's possible ones!

In any case, either if you choose to connect through `Client.java` or if you prefer the graphical interface provided with `GuiClient.java`, after the connection you will be added to a waiting room. Here, you will be able to chat with other players, respectively via CLI if you chose to play with the command line or via GUI if you selected that alternative for the User Interface.

When two players are in the waiting room, a 20-second timer starts counting down: wherever a new player enters the room, the timer resets; if the 20 seconds pass without any new player entering the room, the match starts with those who were waiting as participants. Once the match has begun, a new access to the game will generate a new waiting room with the same rules.

There is no maximum limit for the number of players participating in a match: however, we suggest not having more than 4 players while using the default configuration, since the original game was designed to be balanced for 2-4 participants.


**_(NOTE: Since for this project we decided not to deploy the game on a real server, we made use of constant address and port values. This is easily expandable by prompting the user with the request for IP address and port for the connection to an hypotetical server)_**

##### Configuring game parameters #####

This implementation is designed to be scalable in terms of game configurations.

It specifically relies on the parsing of `.txt` files to dynamically build a proper game model, based on the desired rules and features. Every file must follow a precise syntax, as defined in the next paragraph.

There are some default configuration files within this project, that represent some of the original board game possible configurations.

*(Note: for this project, the only syntax defined is the one for `.txt` files. It can be easily seen from the code structure, though, that this can be extended to any configuration file type, and so can the parsing-dedicated classes)*

###### Dynamic Model Building ######

To set up a proper model for the game, there is the need for several configuration files in a textual format such as `.txt`:

* A file containing **all the bonuses randomly assigned to the map's towns** for this match.

    The syntax for this file is:

    `BonusCode BonusValue:BonusCode BonusValue:...`

    For example, a bonus that gives a player 3 coins and 1 assistant will be written like this:

    `CB 3:AB 1`

* A file containing **the towns' names, qualities and status** for this match.

    The syntax for this file is:

    `TownName:InitialLetter KingPresenceBooleanValue RegionType Alloy`

    For example, the town of Arkon, where there is not the king, is situated in the Coast region and has a Gold alloy type will be written like:

    `Arkon:A false Coast Gold`

* A file containing **the connections between the game's towns** for the current configuration.

    The syntax for this file is:

    `TownName>Neighbor1>Neighbor2>...>NeighborN`

    For example, if the town of Arkon is situated near the towns of Esti and Juvelar:

    `Arkon>Esti>Juvelar`

* A file indicating **the paths to the single map and towns configuration files** within the configuration files' folder.

    This file simply contains the paths to:

    * The file containing the bonuses of the towns;
    * The file containing the towns;
    * The file containing the connections between towns;

    **IMPORTANT: There must be ONE path per line in this file!**

    For the single files configuration, please refer to the points above.

* A file describing **the regions and their properties** for this match.

    The syntax for this file is:

        NumberOfCouncillorsPerRegionalBalcony
        NumberOfUncoveredTilesPerRegion
        RegionType RegionType RegionType ...
    
    The first two lines describe the numbers related to the amount of councillors and uncovered tiles associated with all the regions (every region has the same amount of tiles and councillors as the others), whereas the last line lists all the regions of the map under configuration.

* A file that describes **the rewards corresponding to each bonus tile** in this match.

    The syntax for this file is:

    `RegionName/TownAlloy>VPB NumberOfBonusPoints`

    For example, the Mountain region bonus tile and the Iron Towns bonus tile should be coded like:

    `Mountain>VPB 5`
    `Iron>VPB 5`

    The names of regions and alloys correspond to the original board game's ones.

* A file that describes **the rewards corresponding to each king reward tile** in this match.

    The syntax for this file is:

    `VPB NumberOfBonusPoints`

    This is the same format seen for the first configuration file in this list.

* A file that describes **the bonus distribution on the nobility track** for this match.

    The syntax for this file is:

    `Position>BonusCode BonusValue:BonusCode BonusValue`

    For example, if at rank 2 in the nobility track you gain 2 victory points and 2 coins:

    `2>VPB 2:CB 2`

* A file that indicates **how many councillors of each color are available** for this match.

    The syntax for this file is:

        CouncillorColor1:CouncillorColor1
        CouncillorColor2:CouncillorColor2
        ...

    For example if there are 3 white councillors and 4 orange ones, the file will contain:

        White:White:White
        Orange:Orange:Orange:Orange
        ...

* A file that indicates **how many politic cards of each color are available** for this match.

    This file's syntax is identical to the one for the previous listed file. In addition, the jolly/multicolored cards must be indicated as `AllColors`

* A file that describes **the content of each business permit tile deck by region** for this match.

    The syntax for this file is:

        RegionName
        TownName TownName TownName>BonusCode BonusValue:BonusCode BonusValue
        ...
        STOP
        RegionName
        ...
        END

    For example:

        Mountain
        Arkon Juvelar> VPB 5:CB 2
        ...
        STOP
        Coast
        ...
        END

    The division between regions is made with the `STOP` keyword, whereas the end of the file is marked by the `END` keyword.

In every configuration file, the original game bonuses match the following codes:

* Coin Bonus: `CB`
* Draw Card Bonus: `DCB`
* Assistant Bonus: `AB`
* Victory Point Bonus: `VPB`
* Nobility Bonus: `NB`
* Main Action Bonus: `MAB`
* Reuse City Bonus: `RCB`
* Reuse Permit Bonus: `RPB`
* Take Permit Tile Bonus: `TPB`

##### GUI Support Files #####

In order for the GUI to properly match the given model, some additional configuration files are needed:

* Source images for map, tiles, cards, bonus icons, councillors.

    **_(NOTE: If one does not want to expand the game in terms of used colors or bonuses, there is actually NO NEED to load any icon or source image, except for the map and the configuration files related to it (as specified in the following points of this list). Some of the default configurations already come with the project sources)_**

* An image containing the silhouettes of the towns in the map, filled each with a different color. This is used to match player clicks on the map with the correct town.

    Here's an example of configuration map and the corresponding color mask:

    ![Alt text](https://github.com/alessandroterragni/council-of-four/blob/master/src/img/readme/map.png)
    ![Alt text](https://github.com/alessandroterragni/council-of-four/blob/master/src/img/readme/color%20map.png)

    *(Note: all needed images can be in any format - `.jpg`, `.png`, ... - but it is absolutely important that who produces the color mask makes sure that the colors used match the ones in the corresponding configuration file for their integer RGB code, and makes sure that the given code is correctly read by Java!)*

* A `.txt` file containing the association of integer RGB colors and towns, to correctly interpret the color mask, with the following syntax:

    `IntegerRGBCode : TownName` as in `-65535:Arkon`

    The interpretation essentially consists in the parsing of this `.txt` file: if the clicked area's RGB code matches the one parsed from this file, you can be sure that the player clicked on the corresponding town.

* A `.txt` file containing the pixel coordinates of the spot to put the king counter in, with the following syntax:

    `TownName : CoordX : CoordY` as in `Arkon:32:23`

------------------------------------------------------------------

**_(IMPORTANT NOTE: This way, the game and the map are fully configurable. In an intermediate implementation state, in which the game was only runnable using a CLI, the `Configuration.java` class chose randomly a map file between the available configurations (in the src folder of the project) aptly generating a map based on the chosen model. In the current implementation, the `Configuration.java` class forces the default configuration since the GUI configuration files for other ones are not included in the project. This feature is easily reimplemented by producing the needed GUI configuration files for each map (as described in the above paragraph) and communicating the chosen map to the `GuiClient.java` class through the dedicated instance of the `WelcomeEventMsg.java` class.)_**

### The Game ###

The game follows the same rules as the original *Council of Four™* board game, with the exception of the additional rule of the Market - or *Bazaar* - that was added to comply with the project's requirements.

When the game ends, either naturally or because a player was the only one left in-game, he/she and every other remaining player will be presented with the final ranking, indicating who the winner is and all the participants scores.

Here is a brief showcase of the game interfaces, CLI and GUI:

##### CLI #####

Definitely harder to interact with, the CLI describes everything necessary to the player to progress during his/her turn.

In short, you can:

* Perform an action: `#action ActionNumber` as in `#action 2`

    The available actions' numbers will be printed out by the console.

* Complete an action by giving additional information about it: `#fill`

    Some actions will require the player to input more information regarding the action's target or additional costs. By typing `#fill`, the player can provide said data, while being guided by the console prompts.

* Send a message: `#chat Message` as in `#chat Hello world!`

* Leave the game: `#leave Message` as in `#leave Gotta go, sorry guys.`

* Sell something during the appropriate Bazaar turn: `#sell ThingToSellNumber` as in `#sell 3`

    The numbers indicating salable products will be printed out by the console.

* Buy something during the appropriate Bazaar turn: `#buy ThingToBuyNumber` as in `#buy 1`

    The numbers indicating available-to-buy products will be printed out by the console.

During the various turns, the current player will be guided by the console prompts, so that he/she does not get lost in the heavy-text interface and can always know what is happening during the match.

##### GUI #####

The easier interface to interact with, the GUI consists of one main window and some secondary ones, accessible via buttons on the main one.

Top-left in the main window is the game view, that hosts the **game map**. Below the map is situated the **action control panel**, where the *action buttons* and the *reminder tablet* are. You can click on the reminder tablet to zoom in on it and open the rules window. On the right-hand side of the window there are the **chat box**, the **history log** and the **counters** for the player's status, along with **labels** that allow to see the *possessed cards and tiles* upon hover. Also, there is a **label** that, once clicked, will show a window with a *brief overview on the participants status*. **Hovering over the permit tiles** allows to zoom in and see the tile detail: the zoomed image will be shown over the reminder tablet.

To see the **details of a town**, you have to right-click it on the map: the requested info will appear in a similar fashion to what happens for the zoomed permit tiles. The same concept is applied to the **nobility track**: it will appear once the player hovers on the nobility track counter icon at the bottom-right of the game window.

When the Bazaar phase starts, the window changes to make room to Apu's medieval bazaar. The map hides and leaves room for an empty space that will host the **shelves with the products on sale**. The action buttons change from the regular turn actions to the **bazaar turn actions**.

Some **instructions** are indicated in the reminder tablet. The **current player's name** is written in the main window's title and also appears in the history log, where all the game events and system messages are.

On the **menu bar**, you can access the *sound control*, the *credits* and the *rules*.

*(Note: Due to the fact that all clients run on the same machine, game sound will be played in every GUI window. It is recommended that if more than one window is opened, only one of them remains unmuted. We're sorry for the inconvenience and for the irritating cacophony :( )*

### The implementation ###

##### Game Side #####

This project's design revolves on the use of the **MVC design pattern**; in this case, the "Cocoa" paradigm of the pattern was adopted.

**The Model** contains all the classes that represent the very nature of the game: the map, the players, the cards...

-----------------------------------------------------------------

**The View** manages a complex architecture of message classes that act as a sort of "communication protocol". Two abstract classes are extended by all kinds of messages: `EventMsg.java`, representing the communications sent by the Controller to the View, and `ActionMsg.java`, that follow the opposite route.

The View receives messages through the communication layer and notifies the Controller with other appropriate messages.

-----------------------------------------------------------------

**The Controller** uses two Visitor strategies (instances of the `ActionMsgHandler.java` and `ActionMsgController.java` classes) to check, over two abstraction levels, messages and requests sent over by the View and to react aptly. The Controller processes the message, modifies (if necessary) on the Model and returns informations about the updated Model status, and responses for the player's requests, if any.

The Controller is responsible for maintaining the turn-based game logic, the alternating appearance of regular and Bazaar turns and the modifications to the model.

In a transparent way, a `Game.java` component alternates two other `AbstractGameController.class` entities controlling the game flow: `GameController.class` and `ApuBazaarController.class` manage beginning, turn execution and end of the game and of the Bazaar phase (which is treated, at a high level of abstraction, as a turn-based game inside the main game).

Action messages, if "authorized" and complete of all needed information, are translated into Actions through an instance of the `ActionFactory.java` class (which is in fact a Visitor strategy on the `ToTranslate.java` class objects).

The correctly built Action is performed by the Controller onto the Model. Every type of action returns an `Updater` object (Strategy pattern) that allows the Controller to perform a transparent handling of the "side-effects" caused by the performed action itself.

Another Visitor strategy, `GetBonusTranslator.java` makes it possible for the controller to manage bonus assignment (even for those bonuses that require interaction with the player in form of additional data requests) in a transparent way. The `BonusPack.java` objects are treated by the Controller as single bonuses representing a set of other bonuses.

To avoid exposing the model's rep, the project includes a **tModel**, or **Transferable Model**, that ensures the protection of the real model while providing the users with an apparently easy access to it through the "mask" of the simplified model.

-----------------------------------------------------------------

##### Networking and Connections #####

The networking implementation is based on a transparent application of the publisher-subscribe paradigm on the two connections types: Socket connection and RMI.

Upon start, the server instantiates a `Broker.java` and a `RequestHandler.java`. These are binded to the registry to allow RMI connection.

With RMI, the client uses the `RequestHandler.java` methods to obtain a server communication ID and the `Broker.java` ones to request the subscription. During the subscription process, the RMI client binds a `SubscriberInterface.java` to the registry. The client communicates its SubscriberInterface to the broker during the subscription so that it can be used by the broker to dispatch message.

By using specific components, the Socket connection is handled, with the objective of making transparent to the client the connection and subscription process, that will be divisible by the specific connection type. In fact, the client, in case of a player request to connect via Socket, creates a dedicated object (instance of `SocketClient.java`) that will provide the client itself with a RequestHandler interface and a  Broker, in order to make Socket communication transparent through the right server-side elements; a `ClientHandler.java` and a `SubscriberHandler.java` are initialized by the server to manage incoming Socket connection requests and will call the right methods onto the actual RequestHandler and Broker, returning responses via Socket connection themselves.

Similarly, on the server-side for each active socket the Broker is provided with a `SubscriberInterface.java` object, in order to "hide" the "Socket-managed" message dispatch process. The server-side Broker in fact ends up to have reference to a `Map` `Topic -> PlayerID(player identifier)` and a `Map` `PlayerID -> SubscriberInterface`; updates are dispatched (following these maps) to the SubscriberInterfaces without distinction on the chosen connection type.

Once the connection was set up correctly, the client will communicate with the server through the relative RequestHandler interface; this will transparently process the player's requests.

Here is a graph that summarizes the connections architecture:

![connectionsScheme.png](https://github.com/alessandroterragni/council-of-four/blob/master/src/img/readme/connections.png)

*(Open in a new new tab to view full-resolution image)*

-----------------------------------------------------------------

##### Player Side #####

The available user interfaces, CLI and GUI, are also heavily based on the use of the Visitor pattern: both must include a class that implements ViewHandler.class ,implementing methods to manage the EventMsg notified to the client.

Moreover, this last class is responsible for the `fill` methods (that ensure that the message contains all needed informations) and for sending the proper ActionMsg as answers, by taking advantage of another Visitor pattern: each ViewHandler must be associated with a proper implementation of a `Painter.java` class that will handle the incoming TurnActionMsg, messagges sent by the server to request interaction with the user and must be filled in a precise way, based on the player's chosen action.

This makes player-side handling completely transparent and scalable, since it is possible to create a new type of User Interface and integrate it into the game just by aptly creating and implementing dedicated ViewHandler and Painter.

In detail, for the **CLI**, a `CliHandler.java` and a `CliPainter.java` manage player inputs and print on-screen all useful informations about the incoming messages.

Regarding the **GUI**, instead, a `GuiHandler.java` and a `GuiPainter.java` simply modify the existing JavaX Swing components based on the message informations (all modifications happen strictly inside a JavaX Swing EDT - *Event Dispatcher Thread* - to avoid conflicts), and fill the corresponding TurnActionMsg by using the `Listeners` of all game windows components.

##### Conclusion #####

In conclusion, the main advantage of using the Visitor pattern is the complete transparency of all kinds of operations and the great openness to change and extension.

It is possible, in fact, to define methods in relationship to the static types of abstract classes/interfaces and, calling the `visit` method of the interface on an object with the correct Visitor strategy as a parameter, the required implementation for the right dynamic type can be obtained.

A unique Visitor interface also allows multiple Visitor strategies, making it easier to add functionalities to objects that can be visited. What's more, this implies the complete separation of state and algorithms: the message elaboration algorithms are contained in the Visitor classes are not exposed in the Model ones, perfectly following the good practices defined in the Cocoa paradigm of the MVC pattern.

### Code and Libraries ###

This project was entirely realized using JavaSE8.

Here are listed the tools and libraries used for the project:

* [*Eclipse Mars.2 Release (4.5.2)*](https://eclipse.org/) with the *Architexa Plugin* for tracing UML charts.
* *[SonarQube](http://www.sonarqube.org/) Plugin* for monitoring the evolution of the project.
* *[Maven](https://maven.apache.org/) Plugin* for the dependencies management.
* [*JUnit 4.12*](http://junit.org/junit4/) library for coverage of code with unit tests.
* [*JGraphT 0.9.2*](http://jgrapht.org/) library for the modeling of the game's map with an unoriented unweighted graph.
* [*Google Guava 18.0*](https://github.com/google/guava) library for easy checking methods' preconditions.
* [*JavaX Swing*](https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html) library to model the GUI.

All the tests were run with JUnit 4.12, and the coverage monitoring was made with Maven and SonarQube.

### CONTACTS ###

This repository is jointly owned by Mario Scrocca, Alessandro Terragni and Marco Trabucchi.

This is a university project, designed and realized during the second semester of A.Y. 2015-2016, specifically for the "Prova Finale" project within the "Ingegneria del Sotware" course, held by professors Carlo Ghezzi, Claudio Menghi and Srđan Krstić.

To contact us, please refer to our academic e-mail addresses:

* Mario Scrocca - mario.scrocca@mail.polimi.it

* Alessandro Terragni - alessandro.terragni@mail.polimi.it

* Marco Trabucchi - marco1.trabucchi@mail.polimi.it
