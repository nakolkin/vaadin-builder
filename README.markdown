# Vaadin Builder

VaadinBuilder is inspired by Groovy SwingBuilder and allows you to create Vaadin UI in a declarative manner.

## How to use
Here's a simle  example of using VaadinBuilder:

```groovy
LoginController controller = new LoginController()

Window loginWindow = new VaadinBuilder().window(
        name: "Login Window", modal: true,
        width: "290px", height: "150px",
        closable: false, resizable: false) {
    horizontalLayout(height: "100%", margin: true, spacing: true) {
        label(icon: new ThemeResource("img/key.png"), width: "55px", height: "48px", styleName: "login-key-icon")
        loginForm(onLogin: controller.&onLoginClick)
    }
}

main.addWindow(loginWindow);
loginWindow.center();
```

Where LoginController could be a java class with method: 

```java
public void onLoginClick(LoginForm.LoginEvent e) {
 //do something
}
```