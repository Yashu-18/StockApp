# 📈 StockApp – Modern Stock Market Tracker

**StockApp** is a production-grade Android application built using **Jetpack Compose** and **Hilt**, designed to provide real-time insights into the stock market. Users can search for stocks, track top gainers and losers, and view detailed company information, including price trends and key financial metrics.

## 🚀 Features

- 🔍 **Search Stocks** – Find stocks by company name or ticker symbol.
- 📊 **Top Gainers & Losers** – Real-time market movers to help identify opportunities.
- 🧾 **Detailed Stock Insights** – Overview of stock performance, company financials, industry sector, and more.
- 📈 **Interactive Charts** – Line chart visualization of historical prices using **MPAndroidChart**.
- ⚙️ **Modular Architecture** – Clean separation of concerns for scalability and maintainability.
- 💉 **Hilt-Based Dependency Injection** – Modern and efficient DI framework to manage dependencies seamlessly.
- 🎨 **Jetpack Compose UI** – Fast, modern UI toolkit enabling declarative and reactive user interfaces.

## 🧰 Tech Stack

| Layer         | Technology Used                             |
|---------------|---------------------------------------------|
| UI            | Jetpack Compose, Material3                  |
| Architecture  | MVVM (Model-View-ViewModel)                 |
| DI            | Hilt (Dagger)                               |
| Network       | Retrofit, Gson                              |
| Charting      | MPAndroidChart                              |
| Data Layer    | Repository Pattern (Remote & Local Abstraction) |
| API Provider  | [Alpha Vantage](https://www.alphavantage.co/) |

## 📁 Folder Structure

`com.example.stock`  
├── `data` – Data layer (models, remote API, local cache, repository)  
│&nbsp;&nbsp;&nbsp;&nbsp;├── `local`  
│&nbsp;&nbsp;&nbsp;&nbsp;├── `models`  
│&nbsp;&nbsp;&nbsp;&nbsp;├── `remote`  
│&nbsp;&nbsp;&nbsp;&nbsp;└── `repository`  
├── `di` – Hilt dependency injection modules  
│&nbsp;&nbsp;&nbsp;&nbsp;├── `LocalDataModule.kt`  
│&nbsp;&nbsp;&nbsp;&nbsp;├── `RemoteDataModule.kt`  
│&nbsp;&nbsp;&nbsp;&nbsp;└── `RepositoryModule.kt`  
├── `navigation` – Navigation graph  
│&nbsp;&nbsp;&nbsp;&nbsp;├── `AppNavHost.kt`  
│&nbsp;&nbsp;&nbsp;&nbsp;└── `HomeGraph.kt`  
├── `presentation` – UI layer  
│&nbsp;&nbsp;&nbsp;&nbsp;├── `base`  
│&nbsp;&nbsp;&nbsp;&nbsp;├── `composables`  
│&nbsp;&nbsp;&nbsp;&nbsp;├── `screens`  
│&nbsp;&nbsp;&nbsp;&nbsp;├── `ui`  
│&nbsp;&nbsp;&nbsp;&nbsp;└── `util`  
├── `MainActivity.kt` – App entry point  
└── `StockApp.kt` – Application class  

## 🔐 API Key Setup

This project uses the **Alpha Vantage API** for stock data. To run the app, add your API key in the `local.properties` file:


> ⚠️ **Important:** Never hardcode API keys directly into source files. Use `BuildConfig` or a secrets manager for production.

## 📦 Setup & Run

1. Clone this repository
2. Add your API key to `local.properties`
3. Open the project in Android Studio
4. Sync Gradle and run the app on an emulator or physical device (API 24+)

## 📸 Screenshots

_Add screenshots here if required._

## 🙏 Acknowledgments

- [Alpha Vantage](https://www.alphavantage.co/) for free stock market APIs
- Jetpack Compose & Android Developers for modern UI frameworks
- MPAndroidChart for chart rendering

## 📄 License

This project is built for educational and demo purposes. For commercial usage, please ensure compliance with all third-party API terms and licensing.

---

> Built with ❤️ using modern Android development best practices.
