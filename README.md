# ScoreBoardSystem
 A simple plugin that allows you to use an advanced scoreboard without programming-experience at all

## Build Status
|             | Build Status                                                                                                            |
|-------------|-------------------------------------------------------------------------------------------------------------------------|
| Master      | ![Java CI with Gradle](https://github.com/Shepiii/ScoreBoardSystem/workflows/Java%20CI%20with%20Gradle/badge.svg) |

## Features
- easy to enable / disable
- works with reloading and updates all scoreboards
- easy to use for programmers
- display the ServerGroup of Player easily

## Requirements
- Java 14
- Spigot 1.16.1
- [ProtocolLib](https://github.com/dmulloy2/ProtocolLib/)

## How to use
When putting the plugin on the server, a Config-Folder is created where all settings can be changed.

Display of ServerGroups:
You can display ServerGroups by using %rank% in the default config.
Furthermore, you have to edit the "permissionConfiguration.yml", where you can set the individual groups with permissions.

## For programmers
You can use the AsyncScoreBoardScoreCreate-Event for Triggers, which can e.g. edit single components of the score itself.

## Further Comments
Since PacketWrapper highly recommends not to use the Plugin itself, but only the needed classes, there is an extra Package named "PacketWrapper" where you can find the classes of [it](https://github.com/dmulloy2/PacketWrapper).

## But why though?
As I got regularly asked to create a scoreboard for smaller servers, I thought I could just do it once and upload it to Github.

## License
[MIT](https://choosealicense.com/licenses/mit/)
