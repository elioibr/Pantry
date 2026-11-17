Pantry – Home Pantry and Shopping Assistant

Android app that tracks the items you have at home. When an item runs low, it is added to the shopping list automatically.

Team: Elio Ibrahim · Brian Hakme
Course: Android Development – Final Project (Midterm progress)

Intended users

People who want to track household items and avoid buying products they already have.

Demonstrated feature (midterm)

Add pantry items, change a quantity, and see the resulting shopping list.

Add an item with a name, category, quantity and low-stock threshold.
Change its quantity with the − and + buttons.
When the quantity reaches the threshold (quantity ≤ threshold), the item appears in the shopping list.
Tap Bought to remove it from the list.
Features
Pantry list with quantity controls, a low-stock button and delete
Add item form with validation
Automatic shopping list for low-stock items
Local storage with Room (data stays after closing the app)
Tech stack
Kotlin, Jetpack Compose (Material 3)
MVVM: ViewModel + StateFlow
Room (local database)
Navigation Compose
Requirements
Android Studio (latest stable version)
Android SDK Platform API 37 (install it from Tools → SDK Manager if Android Studio asks)
An Android phone or emulator running Android 8.0 (API 26) or newer
How to run
Clone the repository:
bash
git clone https://github.com/elioibr/Pantry.git
Open the Pantry folder in Android Studio (File → Open).
Wait for the Gradle sync to finish. If a missing SDK is reported, install it from the link in the error message.
Connect a phone with USB debugging enabled, or start an emulator.
Click Run ▶.
How to test the feature
Tap + and add Milk, category Dairy, quantity 2, low at 1.
Tap − once: Milk becomes low stock.
Open the shopping list (cart icon): Milk is there.
Tap Bought: Milk leaves the list.
Close the app completely and reopen it: the data is still there.
Project structure
data/      Room entity, DAO, database and repository (low-stock rule)
screens/   Compose screens, ViewModels, UI state and navigation
PantryApplication.kt   Creates the shared repository
MainActivity.kt        Sets the Compose content
Architecture
Screen → ViewModel → Repository → DAO → Room
   ↑                                        |
   └──────────── Flow (new data) ←──────────┘

Events travel from the screen to the database. New data comes back to the screen through a Flow, so the UI updates automatically.

Next steps
Background reminder for low-stock items (WorkManager)
Edit an existing item
Search and filter by category
Automated tests for the DAO, repository and ViewModels
UI polish
