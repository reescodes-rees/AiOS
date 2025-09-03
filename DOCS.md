# AiOS Technical Documentation

This document provides technical details about the AiOS project, its architecture, and its API.

## Version History

### v0.3 (Current) - Modular Command Architecture
- **Date:** 2025-09-02
- **Changes:**
  - Refactored the `AiService` to use a modular Command-Registry pattern.
  - Introduced the `Command` interface and `CommandRegistry` class.
  - Converted "help" and "createapp" logic into dedicated `Command` classes.
  - Introduced `AiOSCore.java` to act as the central memory and state for the AI, including an event log.

### v0.2 - Basic Chat UI
- **Date:** 2025-08-25
- **Changes:**
  - Implemented a chat-like user interface using Android's `RecyclerView`.
  - Created a `MessageAdapter` to handle displaying messages.
  - Refactored the message model to distinguish between USER and AI senders.
  - Added logic to the `MainActivity` to display a two-way conversation.

### v0.1 - Initial Project Setup
- **Date:** 2025-08-25
- **Changes:**
  - Created the initial Android project structure (`build.gradle`, `AndroidManifest.xml`).
  - Added `README.md` for project overview and `JULES.md` for task tracking.
  - Implemented a basic "Hello World" `MainActivity`.

## Architecture Overview

The AiOS application is built on a modular, service-oriented architecture designed for extensibility. The core components work together as follows:

1.  **`MainActivity.java`**: The main entry point and the UI layer of the application. It is responsible for rendering the chat interface and capturing user input. It holds instances of the `AiOSCore` and `AiService`.

2.  **`AiOSCore.java`**: The "brain" or "memory" of the AI. It is a central class that holds the AI's state, such as the event log. It is designed to be passed to various services that need to read from or write to the AI's state.

3.  **`AiService.java`**: The primary service that processes user input. It doesn't contain any command logic itself. Instead, it uses the `CommandRegistry` to delegate the work to the appropriate command module.

4.  **Command Pattern (`commands/` package)**: This is the heart of the AI's capabilities.
    *   **`Command.java`**: An interface that defines a standard for all executable commands.
    *   **`CommandRegistry.java`**: A manager class that discovers and holds a map of all registered `Command` objects.
    *   **`*Command.java` (e.g., `HelpCommand.java`)**: Concrete implementations of the `Command` interface. Each class is responsible for a single, specific action. This design makes it easy to add new functionality without modifying the core services.

## How to Add a New Command (API Guide)

Adding a new capability to the AI is straightforward thanks to the Command Pattern. Follow these steps:

1.  **Create a New Command Class:**
    *   Create a new Java class in the `app/src/main/java/com/aios/commands/` package.
    *   The class must implement the `Command` interface.
    *   Example: `public class MyNewCommand implements Command { ... }`

2.  **Implement the Interface Methods:**
    *   `getName()`: Return the string that will trigger your command (e.g., `"mynewcommand"`).
    *   `getDescription()`: Return a short, user-friendly description of what the command does.
    *   `execute(List<String> args, AiOSCore core, CommandRegistry registry)`: This is where you put your command's logic.
        *   Use the `args` list to handle any additional parameters.
        *   Use the `core` object to log events or interact with the AI's state.
        *   Return a `String` containing the result or message to be displayed to the user.

3.  **Register the New Command:**
    *   Open `app/src/main/java/com/aios/AiService.java`.
    *   In the `registerCommands()` method, add a new line to register an instance of your new command class.
    *   Example: `registry.registerCommand(new MyNewCommand());`

That's it! The `AiService` and `HelpCommand` will automatically discover and use your new command the next time the app is run.
