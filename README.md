# NotificationApp - Installation Guide and test FCM
## Installation
```
git clone https://github.com/ParisseNicolas/notificationApp.git
./gradlew clean assembleDebug
```

Then get the app/build/outputs/apk/debug/app-debug.apk and install it on your smartphone.

## Get FCM token
1. Authorize the notifications for this application on your smartphone
1. Open the application
1. <YOUR_FCM_TOKEN> just appear there, copy it for next

## Get your Firebase project id
1. Go to [console.firebase.google.com](console.firebase.google.com)
1. Create a project
1. Go to project settings (⚙️)
1. Get <YOU_PROJECT_ID> as project id of the general settings

## Get the google-servies.json
1. Go to the general settings of you Firebase project
1. Go to the Applications section
1. Select your application
1. Click on the google-services.json button to download it

## Configure gcloud
``` 
curl -O https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-cli-456.0.0-linux-x86_64.tar.gz
tar -xf google-cloud-cli-456.0.0-linux-x86_64.tar.gz
./google-cloud-sdk/install.sh
source ./google-cloud-sdk/path.bash.inc
gcloud auth activate-service-account --key-file=google-services.json
gcloud config set project <YOUR_PROJECT_ID>
ACCESS_TOKEN=$(gcloud auth print-access-token)
``` 

## Final curl notification push
```
curl -X POST \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  https://fcm.googleapis.com/v1/projects/<YOUR_PROJECT_ID>/messages:send \
  -d '{
    "message": {
      "token": "<YOUR_FCM_TOKEN>",
      "data": {
        "title": "Alerte",
        "body": "Test data-only",
        "channel_id": "alerts_channel_2",
        "sound": "alert_sound"
      }
    }
  }'
```