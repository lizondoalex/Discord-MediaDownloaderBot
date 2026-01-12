# Media Downloader Bot

A Java (JDA) Discord bot to download video and audio from YouTube and social media using yt-dlp. Supports User App installation and validates Discord's 25MB file size limit.

## Requirements

* Docker (Recommended)
* For manual run: Java 17+, Maven, and yt-dlp.

## Configuration

Create a `.env` file in the project root with the following content:

DISCORD_TOKEN=your_token_here

## Docker Execution Using My Image

Just run:

docker run -d \
    --name mediadownloaderbot \
    -e DISCORD_TOKEN="your_token_here" \ 
    lizondoalex/media-downloader-bot:latest

## Docker Execution Without My Public Image

1. Build the image:
docker build --no-cache -t media-downloader-bot .

2. Run the container:
docker run -d \
  --name mediadownloaderbot \
  -e DISCORD_TOKEN="your_token_here" \ 
  media-downloader-bot

## Commands

* /download [url] [format]
  - url: Link to the content.
  - format: mp3 (audio) or mp4 (video).

## Notes

* Files are temporarily saved in the /downloads directory before uploading.
* If the file exceeds 25MB, the download will be automatically cancelled.