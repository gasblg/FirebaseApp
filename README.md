# FirebaseApp
<table> 
      <tr>
        <td>
            Login
        </td>
        <td>
            Main screen
        </td>
        <td>
            Add new item
        </td>
    </tr>
    <tr>
        <td>
          <img src="https://github.com/gasblg/FirebaseApp/raw/main/media/Screenshot_20230927_200935.png" width="256"/>
        </td>
        <td>
          <img src="https://github.com/gasblg/FirebaseApp/raw/main/media/Screenshot_20230926_131503.png" width="256"/>
        </td>
        <td>
          <img src="https://github.com/gasblg/FirebaseApp/raw/main/media/Screenshot_20230926_131536.png" width="256"/>
        </td>
    </tr>
</table>

## Firebase

- firebase auth 
- firebase remote config 
- firebase realtime database (CRUD)
- firebase crashlytics
- firebase analytics
- firebase app distribution (with feedback)

## App structure

&ndash; dagger2<br>
&ndash; mvvm<br>
&ndash; datastore<br>
&ndash; multi modules<br>
&ndash; build-logic<br>
&ndash; clean architecture<br>

## Needed for work

add `google-service.json`<br> 
create file `params.txt` with property `firebase_app_id=`<br> 
add rules for you realtime database:
```
{
  "rules": {
    "users": {
      "$uid": {
      ".read": "auth.uid === $uid",
      ".write": "auth.uid === $uid",
          }
        },
        "items": {
         "$uid": {
        ".read" : "auth.uid === $uid",
        ".write" : "auth.uid === $uid",
          }
        }
      }
}
```
## For upload

you can use tasks:
```
distribution:
  - uploadAll
  - uploadDebug
  - uploadRelease
```
