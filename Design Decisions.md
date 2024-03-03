# Document for detailing project decisions
Records are read only and can only be overruled but not edited.

## records
1. Using a multi project approach
    - Reason: Using a library for common objects and logic makes organization easier and avoids code duplication.
    - Technical details: I broken the architecture up into three pieces:
        - Core: Code that is used by both the frontend and backend. Also responsible for handling common logic
        - Backend: Responsible for large scale communication with players
        - Frontend: Responsible for user input and communicating with the backend server

2. Using websockets for realtime communication
    - Reason: In the javascript ecosystem, real time communication is more often than not made possible through websockets. It is widely used and has great support in other ecosystems as well.
    - Technical details: Using `org.java-websocket.Java-WebSocket` for my choice of websocket library as it has proven to be quite good in a previous project.

3. Creating and keeping client connections with sessions
    - Description: Altough websockets give back a "session object", that object is only alive until the connection is. If dropped, that session can no longer be tied to the person wanting to reconnect.
    - Reason: To perserve the connection and game data that might be lost if the person accidentaly disconnects during the game, the user session will be tied to an ID, and not the session provided by the websocket implementation
    - Technical details: Sessions must be initialized. Data for session initialization must be sent to the Server. Keeping track of the user session with a `sessionId`. User sessions are randomly generated UUIDs and must not be shared with other players because that would mean they can control the player.

4. Message passing: JSON format
    - Reason: JSON is the standard format for web applications and I don't think there is a need to stray from the standard.
    - Technical details: Using the `com.google.code.gson.gson` library for encoding and decoding message.

5. Message passing: the `Message` class
    - Description: The `Message` class will provide the common schema which all messages must follow.
        - The `Message` must be able to be used for:
            - As the user:
                - creating a user session
                - heartbeating
                - reconnecting to an existing user session
                - hosting a game
                - joining a game
                - setting a ship
                - sending a torpedo

            - As the server sending dispatch events:
                - acknowledging commands/heartbeat 
                - giving a `sessionId`
                - giving information about the ongoing game for reconnected user session if applicable
                - giving game information such as `gameId`
                - notifying of placement type changing (ship or torpedo)
                - notifying of receiving a torpedo
                - notifying of hitting a target with a torpedo or not
                - notifying of a turn change
                - notifying of a game ending
    - Reason: Need a schema for standard message passing
    - Technical details: Messages will consist of a `type` and a `payload`. Payloads are DTOs that must implement the `MessagePayload` interface which will be used to validate the object.

6. Defining the game:
    - Description: The classic game of battleship consists of two players taking turns trying to sink the others ships that they've placed down. The board is 10x10 and the ships are [2, 3, 3, 4, 5] in length. The ships can be placed vertically or horizontally within the board and not on top of each other. The winner is the one who manages to sink all the ships of the other person. For our purpose this is a bit limiting. The **definition we'll be using is the following**: `playerCount` amount of players must place down `shipCount` amount of ship with sizes ranging from 2 to `shipCount` on their `boardSize`x`boardSize` boards. Once done, the players must pick a point on a different players board. They are notified if it was a hit and if it resulted in a sink or not. They will also be notified of receiving a torpedo. Once everyone sent a torpedo, another round begins until all other players ships have been sunk and only one remains. At defeat or the end of the game, players receive stats about their performace.
    - Technical details: The game state consists of: 
        - a game id
        - playerCount
        - shipSizes
        - shipCount
        - boardSize
        - placement of ships and torpedos
        - hitCount (to easily check if all the ships have been hit or not)

7. Using `Result<Ok, Err>` instead of throwing exceptions:
    - Reason: I became quite fond of Rust's error handling, because I can see what may go wrong. The exception to this is assertions, which will be used in case we assume a value must be a certain way.

8. Using structured logging:
    - Reason: Easier time debugging if you have a bunch of information.
    - Technical details: Using `slf4j` with `logback`. Logging to disk. See: [this blog post](https://www.baeldung.com/java-structured-logging) 
