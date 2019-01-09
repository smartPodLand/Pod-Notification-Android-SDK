#PodNotify
Android notification SDK that based on [Async](https://github.com/smartPodLand/Pod-Async-Android-SDK) and works with Fanap's POD service.

## First step
add this line to Application class:
```java
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // ...
        PodNotify.setApplication(this);
    }
}
```
and initialize and build the Notification module:
```java
public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
        
        PodNotify podNotify = new PodNotify.builder()
                            .setAppId("APP_ID")
                            .setServerName("SERVER_NAME")
                            .setSocketServerAddress("SERVER_SOCKET_ADDRESS")
                            .setSsoHost("SSO_HOST_IF_NEEDED")
                            .setToken("TOKEN")
                            .build(); 
    }
}
```
and then start the service:
```java
podNotify.start(getApplicationContext());
```
## Implement Services
#### MessagingService
to get the notification and notification extras, create a **service** that extends from `PodMessagingService`. 
```java
public class MyMessagingService extends PodMessagingService {

    @Override
    public void onMessageReceived(Notification notification) {
        //your code that use Notification object
        super.onMessageReceived(notification);
    }
}
```
**attention:** Use `super.onMessageReceived(notification);` if you want to display the received message automatically; Otherwise it can be ignored.

and then declare the service in `Android Manifest`:
```xml
        <service
            android:name=".MyMessagingService">
            <intent-filter>
                <action android:name="com.fanap.podnotify.MESSAGING_EVENT"/>
            </intent-filter>
        </service>
```
**attention:** do not remove/change action from declaration.

#### InstanceIdService
to get the device peer id on every change,  create a **service** that extends from `PodInstanceIdService`.
```java
public class MyInstanceIdService extends PodInstanceIdService {

    @Override
    public void onPeerIdChanged(String peerId) {
        //your code... (eg. send PeerId to your server)
        super.onPeerIdChanged(peerId);
    }
}
```
and then declare the service in `Android Manifest`:
```xml
        <service
            android:name=".MyInstanceIdService">
            <intent-filter>
                <action android:name="com.fanap.podnotify.INSTANCE_ID_EVENT"/>
            </intent-filter>
        </service>
```
**attention:** do not remove/change action from declaration.

## Notification Object
```java
public class Notification {

    private String title;
    private String text;
    private Long messageId;
    private String senderId;
    private Map<String,String> extras;
    
    // Getters and setters for fields.
}
```

## Contact me
If you have ideas or feedback, feel free to open up issues, and/or [contact me](mailto:hi@arvinrokni.ir).