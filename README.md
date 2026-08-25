# SauceLabs Front End Automation

Automated UI test suite for [SauceDemo](https://www.saucedemo.com/), built with **Java**, **Selenium WebDriver**, and **JUnit 5**, following the **Page Object Model (POM)** design pattern.

The suite covers the full shopping flow of the SauceDemo application — login, product browsing/sorting, cart management, checkout (both the standard and V2 checkout flows), and shared UI components (header and footer) — including functional assertions as well as UI/style checks (fonts, colors, hover states).

## Tech Stack

| Tool | Purpose |
|---|---|
| Java | Programming language |
| Maven | Build tool & dependency management |
| Selenium WebDriver 4.44.0 | Browser automation |
| JUnit 5 (Jupiter) | Test framework (`@Test`, `@ParameterizedTest`, `@BeforeEach`, `@AfterEach`) |
| JUnit 4 | Legacy assertion support (`org.junit.Assert`) used in some tests |
| ChromeDriver | Browser driver used to run tests in Google Chrome |

## Project Structure

```
FrontEndAutomation/
├── pom.xml                                 # Maven project config & dependencies
└── src
    ├── main/java/pages/                    # Page Object classes (POM)
    │   ├── LoginPage.java                  # Login form actions, validations & UI styling checks
    │   ├── ProductsPage.java               # Product listing, sorting, hover colors
    │   ├── CartPage.java                   # Cart actions (add/remove items, checkout)
    │   ├── CheckOutPage.java                # Checkout step one (customer info form)
    │   ├── CheckOutV2Page.java              # Checkout step two (order overview) - V2 flow
    │   ├── CheckOutCompletePage.java        # Order confirmation page
    │   ├── Header.java                      # App header / burger menu navigation
    │   └── Footer.java                      # Footer links (Twitter, Facebook, LinkedIn)
    └── test/java/
        ├── CartPageTests.java               # Cart page test cases
        └── saucedemotests/
            ├── LoginPageTests.java          # Login flow & validation tests
            ├── ProductsPageTests.java       # Product listing & sorting tests
            ├── CheckOutPageTests.java       # Checkout step one tests
            ├── CheckOutV2PageTests.java     # Checkout step two tests
            ├── CheckOutCompletePageTests.java # Order confirmation tests
            ├── HeaderTests.java             # Header/navigation tests
            └── FooterTests.java             # Footer link tests
```

## Design Pattern: Page Object Model

Each page/section of the application under test has a dedicated class inside `src/main/java/pages/`. Each Page Object:

- Encapsulates its own element locators (`By` selectors) as private fields.
- Exposes public methods for **actions** (e.g. `clickLogin()`, `enterUsername()`) and **getters** used for assertions (e.g. `getPageTitle()`, `getErrorMessageColor()`).
- Takes a `WebDriver` instance via its constructor, so it can be reused across multiple test classes.

Test classes only interact with these Page Object methods — they never query the DOM directly — which keeps tests readable and easy to maintain if the UI changes.

## What's Covered

- **Login (`LoginPage`)** — successful/unsuccessful login combinations (wrong password, wrong username, missing fields), all SauceDemo user types (`standard_user`, `locked_out_user`, `problem_user`, `performance_glitch_user`, `error_user`, `visual_user`), error message display/dismissal, and UI styling checks (fonts, font sizes, button colors).
- **Products (`ProductsPage`)** — page title, sort-dropdown options, ascending/descending price sorting, product name hover colors, burger menu hover states.
- **Cart (`CartPage`)** — adding/removing items, item names & prices, cart item count, proceeding to checkout.
- **Checkout (`CheckOutPage`, `CheckOutV2Page`, `CheckOutCompletePage`)** — customer information form, order overview (V2 step), and order confirmation.
- **Header (`Header`)** — burger menu navigation (All Items, About, Logout, Reset App State).
- **Footer (`Footer`)** — social links (Twitter, Facebook, LinkedIn) and footer copy text.

**57 test methods** across **8 test classes**.

## Prerequisites

- **JDK 11+** (or a compatible JDK for Selenium 4.44.0 / JUnit 5.12.2)
- **Maven** 3.6+
- **Google Chrome** installed (tests run against `ChromeDriver`)
- Internet access — tests run against the live demo site `https://www.saucedemo.com/`

> This project uses Selenium 4's built-in driver management, so a matching `chromedriver` binary is resolved automatically for your installed Chrome version — no manual driver download is required.

## Getting Started

Clone the repository:

```bash
git clone https://github.com/Muki029/SauceLabsFrontEndAutomation.git
cd SauceLabsFrontEndAutomation
```

Install dependencies:

```bash
mvn clean install
```

## Running the Tests

Run the full test suite:

```bash
mvn test
```

Run a single test class:

```bash
mvn test -Dtest=LoginPageTests
```

Run a single test method:

```bash
mvn test -Dtest=LoginPageTests#successfulLoginTest
```

Each test class opens a fresh Chrome browser window before every test (`@BeforeEach`) and closes it afterward (`@AfterEach`), so tests are fully independent of one another.

## Notes

- Tests run **headed** (a visible Chrome window) by default via `new ChromeDriver()`.
- Some tests use `Thread.sleep()` to wait for animations/popups to settle before interacting with elements.
- `LoginPage.pressSpacebar()` uses `java.awt.Robot` to dismiss a native browser permission popup that can appear on login — this only works in an environment with an active display/desktop session.

## Author

Muhamed Mavmudoski ([@Muki029](https://github.com/Muki029))
