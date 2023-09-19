# NautaClear
<p align="center">

<img src="./images/logo.png" width="40%">

Librería para obtener la cantidad de correos en el servidor nauta y eliminarlos.

[![](https://jitpack.io/v/applifycu/nautaclear.svg)](https://jitpack.io/#applifycu/nautaclear)

</p>

## Agregar

1. Agrega a la raíz de su proyecto

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
   }
}
```
    
2. Agregar la dependencia a su proyecto
    
```groovy
buildTypes {
    release {
      isMinifyEnabled = false
   }
}

dependencies {
   implementation 'com.github.applifycu:nautaclear:1.0.0'
}
```
    
## Uso

En su MainActivity.class agregue

```java

NautaMail nauta = new NautaMail(this);

// obtener cantidad de correos
mail.obtainsEmail(email,password,new NautaMail.EmailsCallback() {
    @Override
    public void updateUI(MailCount count) {
    String inbox = count.inboxCount;
    String trash = count.trashCount;
    
    System.out.printIn(inbox);
    System.out.printIn(trash);
}
  @Override
  public void handleException(Exception e) {
    System.out.printIn("Error: "+ e);
    }
});
 
 
 // eliminar correos 
mail.deleteEmails(email,password,new NautaMail.EmailsCallback() {
    @Override
    public void updateUI(MailCount count) {
    
    String inbox = count.inboxCount;
    String trash = count.trashCount;
                                        
}
@Override
public void handleException(Exception e) {
    System.out.printIn("Error: "+ e);
    }
});
 ```
 
 Su AndroidManifest
 
 ```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### Contacto

Para dudas o sugerencias puede ponerse en contacto con nosotros en:

soporteapplify@gmail.com

### License
 
 ```
    Copyright (C) 2023  Applify

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
    
