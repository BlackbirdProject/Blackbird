# Blackbird App

## Descripción
Blackbird es un proyecto OpenSource con el que queremos conectar con la ciudadanía de Madrid, por
eso hemos creado una aplicación, dónde cualquier desarrollador con conocimientos pueda colaborar
y ayudar a la gente a crear una experiencia de movilidad basada en Madrid.

## Requirements
As this is a Firebase connected app, it needs a serviceAccount.json file on the app module  
An api.xml on the modules folder is also required with the the following information  
You also need to register the App on Places API with your unique SHA-1 signing certificate

````
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="google_maps_api_key" templateMergeStrategy="preserve" translatable="false">YOUR_GOOGLE_MAPS_API_KEY</string>
    <string name="otp_server_url" templateMergeStrategy="preserve" translatable="false">OTP_INSTANCE_URL</string>
    <string name="uber_server_token" templateMergeStrategy="preserve" translatable="false">UBER_SERVER_TOKEN</string>
    <string name="firebase_web_client_id" templateMergeStrategy="preserve" translatable="false">YOUR_FIREBASE_WEB_CLIENT_ID</string>
</resources>

````
