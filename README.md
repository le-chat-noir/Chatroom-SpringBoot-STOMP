# Chatroom-SpringBoot-STOMP

This chatroom has the same UI as my Chatroom project, only this one is built upon Spring Boot and STOMP rather than vanilla websocket.
Function similar to another one but different approach.

---
basic functions:

    user can send text message.
    user can send image as message, image is resized to 200px * 200px jpg, and send as Base64 encoded string. (image file not store at local directory).
    user joined with having session attribute "nickName" can send message to specific user, guest can only send public text/image to everyone.

*This chatroom stores nothing into cookies nor server side, refresh browser wipes all history.
