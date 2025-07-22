# AmericanEagleTestProject

[![Java](https://img.shields.io/badge/Java-17-%23ED8B00?logo=openjdk)](https://www.java.com/)
[![Gradle](https://img.shields.io/badge/Gradle-8.1-%2302303A?logo=gradle)](https://gradle.org/)
[![Allure](https://img.shields.io/badge/Allure-Report-%23FF6A00?logo=allure)](https://allurereport.org/)

Это репозиторий для дипломного проекта по автоматизации тестовых сценариев для сайта [American Eagle](https://www.ae.com/us/en) с использованием UI и API тестов.  

**American Eagle** — американская розничная компания по продаже одежды и аксессуаров, со штаб-квартирой в Питтсбурге, штат Пенсильвания.  
American Eagle также является материнской компанией Aerie. По состоянию на январь 2021 года работает 901 магазин American Eagle Stores, 175 магазинов Aerie и 2 магазина Todd Snyder в США, Канаде, Мексике и Гонконге. Среди популярных продуктов бренда — джинсы, рубашки поло, футболки, верхняя одежда и купальники.

## Содержание
- [🛠️ Технологический стек](#-технологический-стек)
- [📈 Тест план](#-тест-план)
- [🚀 Запуск тестов](#-запуск-тестов)
- [⚙️ Запуск в Github Actions](#-запуск-в-github-actions)
- [📊 Allure отчет в Github Actions](#-allure-отчет-в-github-actions)

---

## 🛠️ Технологический стек
<p align="center">
  <a href="https://www.jetbrains.com/idea/" rel="nofollow"><img width="10%" title="IntelliJ IDEA" src="images/logo/Intelij_IDEA.svg" alt="Intellij_IDEA" style="max-width: 100%;"></a>
  <a href="https://www.java.com/" rel="nofollow"><img width="10%" title="Java" src="images/logo/Java.svg" alt="Java" style="max-width: 100%;"></a>
  <a href="https://www.selenium.dev/" rel="nofollow"><img width="10%" title="Selenium" src="images/logo/Selenium.png" alt="Selenium" style="max-width: 100%;"></a>
  <a href="https://rest-assured.io/" rel="nofollow"><img width="10%" title="Rest Assured" src="images/logo/RestAssured.svg" alt="RestAssured" style="max-width: 100%;"></a>
  <a href="https://gradle.org/" rel="nofollow"><img width="10%" title="Gradle" src="images/logo/Gradle.svg" alt="Gradle"></a>
  <a href="https://junit.org/junit5/" rel="nofollow"><img width="10%" title="JUnit5" src="images/logo/JUnit5.svg" alt="JUnit5" style="max-width: 100%;"></a>
  <a href="https://allurereport.org/" rel="nofollow"><img width="10%" title="Allure Report" src="images/logo/Allure_Report.jpg" alt="Allure" style="max-width: 100%;"></a>
  <a href="https://github.com/" rel="nofollow"><img width="10%" title="GitHub" src="images/logo/GitHub.svg" alt="GitHub" style="max-width: 100%;"></a>
  <a href="https://github.com/features/actions" rel="nofollow"><img width="10%" title="Github Actions" src="images/logo/Github_Actions.svg" alt="Github Actions" style="max-width: 100%;"></a>
</p>

- **Язык программирования:** Java 17
- **UI тестирование:** Selenium
- **API тестирование:** REST assured
- **Сборка:** Gradle
- **Тестовый фреймворк:** JUnit 5
- **Шаблон проектирования:** Page Object Model (POM)
- **Упрощение создания моделей в API тестировании:** Lombok
- **Отчетность:** Allure Report
- **CI/CD:** GitHub Actions, который создаёт Allure отчёт и публикует результаты на GitHub Pages.

**Содержание Allure отчёта:**
- Шаги тестов
- Автоматические скриншоты для упавших UI-тестов (кроме тестов с секретными данными)
- Page Source для упавших UI-тестов  

<p align="center"> <img src="images/failed_test_screenshot.JPG" alt="Failed test: screenshot" width="700"/> </p>
<p align="center"> <img src="images/failed_test_html.JPG" alt="Failed test: html" width="700"/> </p>

---

## 📈 Тест план

<p align="center"> <img src="images/AE_test_plan.pdf" alt="Test plan" width="700"/> </p>

![Test plan](images/AE_test_plan.pdf)

---

## 🚀 Запуск тестов

Для локального запуска тестов с авторизацией **заполните поля** email и password в файле default.properties.

⛔️ Мы тестируем реальный сайт работающего магазина, поэтому некоторые тесты **блокируются защитой от ботов (Akamai)**: не работают тесты, которые связаны с **авторизацией и регистрацией** на сайте.
В связи с этим в данном проекте эти тесты отмечены тегом "Defect" и для их локального запуска используется отдельная команда. 

### Команды для запуска:

Все тесты (кроме дефектных):
   ```bash
      gradle allExceptDefect
   ```

Только smoke-тесты (кроме дефектных):
   ```bash
      gradle smoke
   ```

Только API-тесты (кроме дефектных):
   ```bash
      gradle apiTests
   ```

Только UI-тесты (кроме дефектных):
   ```bash
      gradle uiTests
   ```

Только defect-тесты:
   ```bash
      gradle defect
   ```

Полный прогон всех тестов:
   ```bash
      gradle test
   ```

Для удаленного запуска в GitHub Actions используется команда:  
`./gradlew allExceptDefectRemote -Denv=default`

---

## ⚙️ Запуск в Github Actions
1. Перейдите в репозиторий `AmericanEagleTestsProject`  

2. Откройте вкладку `Actions`  
<p align="center"> <img src="images/01_press_to_actions_tab.png" alt="Press to Actions tab" width="700"/> </p>

3. Выберите workflow `AE Tests`  
<p align="center"> <img src="images/02_press_ae_tests_workflow.png" alt="Press AE tests workflow" width="700"/> </p>

4. Нажмите `Run workflow`  
<p align="center"> <img src="images/03_press_run_workflow.png" alt="Press run workflow" width="700"/> </p>

5. Дождитесь завершения выполнения

---

## 📊 Allure отчет в Github Actions
1. После завершения сборки перейдите в `Actions` снова  
<p align="center"> <img src="images/04_press_to_actions_tab.png" alt="Press to Actions tab" width="700"/> </p>

2. Нажмите на `pages and deployment`  
<p align="center"> <img src="images/05_click_on_the_pages_and_deployment.png" alt="Click on the Pages and deployment" width="700"/> </p>

3. Перейдите по ссылке в отчет  
<p align="center"> <img src="images/06_click_on_the_link.png" alt="Click on the link" width="700"/> </p>

4. Просмотрите отчет  
<p align="center"> <img src="images/07_allure_report.png" alt="Allure report" width="700"/> </p>

<p align="center"> <img src="images/08_allure_report.png" alt="Allure report" width="700"/> </p>