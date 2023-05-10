# Video App  Assessment Requested By SilverOrange Completed by Alwaleed Hamam

This repository contains an Android implementation of the silverorange Native App Developer Assessment. The goal of this project is to create a simple video player app with a scrollable details section.

## Features

- Display a screen with a video player at the top and a scrollable details section at the bottom.
- Use provided image assets for media controls.
- Fetch a list of videos from the provided API.
- Sort the received list of videos by date.
- Load the first video into the UI by default.
- Implement play/pause button for the video player.
- Implement next/previous buttons for the video player.
- Show the returned description for the current video as rendered Markdown.
- Display the title and author of the current video.

## Environment

The project is implemented using Android Studio, Kotlin, and XML layouts.

## Dependencies

The project uses the following external dependencies:

- [ExoPlayer](https://github.com/google/ExoPlayer#using-exoplayer) for media playback
- [Markwon](https://noties.io/Markwon/docs/v4/install.html) for Markdown rendering
- [Gson](https://github.com/google/gson#download) for JSON parsing
- [OkHttp](https://square.github.io/okhttp/#releases) for HTTP requests
- [Retrofit](https://square.github.io/retrofit/#download) for HTTP requests and parsing

## Getting Started

1. Clone the repository.
2. Open the project in Android Studio.
3. Run the provided API server (instructions can be found in the original task description).
4. Build and run the app on an emulator or physical device.

## API Server

The API server provided for this task serves a list of videos. The server runs by default on `localhost:4000` with the following endpoint:

- `http://localhost:4000/videos` - returns a JSON-encoded array of videos.
