# BolosAssuncao

## Tech Stack & Open-Library Sources : 
+ Minimum SDK level 21
+ 100% Kotlin based + Coroutines
+ JetPack
   + LiveData - notify domain layer data to views.
   + Lifecycle - dispose observing data when lifecycle state changes.
   + ViewModel - UI related data holder, lifecycle aware.
   + Room Persistence - construct database.
   + Jetpack Navigation
+ Firebase Notifications
+ Data Binding
+ Architecture
   + MVVM Architecture (View - DataBinding - ViewModel - Model)
   + Repository pattern

+ Material Design & Animations
   + LottieFlies
  
+ Retrofit2 & Gson - constructing the REST API


## GOAL : 

Build a product with a login system for customers and the "privilaged/business side users" to allow p2p communication and product ordering.

In the meantime practicing Kotlin and other industry standard techiniques and libaries.

###### (Current scope only aim at one business)
###### Requirements :

- All users must be able to login
- Privilaged users must have an option to add new products
- Privilaged users must have an option to delete/edit products
- Privilaged users should be able to manage orders
- Privilaged users must be able to use the chat to speak to customers

- Ordinary users must be able to view all products
- Ordinary users must be able to create an order
- Ordinary users must be able to contact the privilaged users



## Current User UI : In progress

![home screen](images/neutral_home1.png) 
![login screen](images/neutral_login1.png) 
![register screen](images/neutral_register1.png) 
![product screen](images/neutral_preview1.png)
![basket screen](images/neutral_basket1.png)
![delivery screen](images/neutral_delivery1.png)

## Current Admin UI : In progress

![current products](images/admin_home1.png) 
![edit product](images/admin_edit1.png)
![new product](images/admin_new1.png)
