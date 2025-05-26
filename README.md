# 📈 StockApp – Modern Stock Market Tracker

**StockApp** is a modern Android stock market tracking app built using **MVVM architecture**, **Hilt**, and **Retrofit**. It allows users to explore top gainers & losers, search stocks in real-time, and view detailed company insights with interactive charts.

## 🚀 Features

- 🔍 **Search Stocks** – Find stocks by company name or ticker symbol using Alpha Vantage.
- 📊 **Top Gainers & Losers** – See real-time market movers with price and % change.
- 📈 **Detailed Stock View** – Company description, stats, and an interactive line chart for recent prices.
- 🧾 **Recent Searches** – Smart auto-suggestions and recently searched stocks.
- 📉 **Pagination Support** – Paginated view of gainers/losers with lazy loading.
- 💾 **Local Caching** – Uses Room DB to cache stock data for offline availability.
- ⚙️ **Error Handling** – Clean fallbacks with API error safety and UI feedback.

## 🧰 Tech Stack

| Layer               | Technology Used                               |
|---------------------|-----------------------------------------------|
| UI                  | Jetpack Compose / XML                         |
| State Management    | LiveData, ViewModel                           |
| Architecture        | MVVM                                          |
| Dependency Injection| Hilt (Dagger)                                 |
| Networking          | Retrofit, OkHttp, Gson                        |
| Charts              | MPAndroidChart                                |
| Caching             | Room DB, SharedPreferences (optional)         |
| Pagination          | Paging 3                                      |
| API Provider        | [Alpha Vantage](https://www.alphavantage.co/) |

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

## Screenshots

<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/9820f096-addd-4ada-9bfd-1393b521e334" alt="Top Gainers" width="220" height="450" /><br/>Top Gainers</td>
    <td><img src="https://github.com/user-attachments/assets/e97f0055-59b2-42dd-b9a9-792ad2da6845" alt="Search Functionality" width="220" height="450" /><br/>Search Functionality</td>
    <td><img src="https://github.com/user-attachments/assets/b67cdc78-d16f-4ad0-a6e0-bebb4fe2d41e" alt="Top Losers" width="220" height="450" /><br/>Top Losers</td>
    <td><img src="https://github.com/user-attachments/assets/7eda0290-eb36-4c4d-99f2-7026c49e885d" alt="Detail Screen" width="220" height="450" /><br/>Detail Screen</td>
  </tr>
 
</table>


## 🔐 API Key Setup

This project uses the **Alpha Vantage API** for stock data. To run the app, add your API key in the `local.properties` file:


> ⚠️ **Important:** Never hardcode API keys directly into source files. Use `BuildConfig` or a secrets manager for production.

## 📦 Setup & Run

1. Clone this repository
2. Add your API key to `local.properties`
3. Open the project in Android Studio
4. Sync Gradle and run the app on an emulator or physical device (API 24+)


## 🙏 Acknowledgments

- [Alpha Vantage](https://www.alphavantage.co/) for free stock market APIs
- Jetpack Compose & Android Developers for modern UI frameworks
- MPAndroidChart for chart rendering

## 📄 License

This project is built for educational and demo purposes. For commercial usage, please ensure compliance with all third-party API terms and licensing.

---

> Built with ❤️ using modern Android development best practices.
