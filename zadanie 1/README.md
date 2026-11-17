# Zadanie 1 & 2 - Aplikacja Quiz na Androida

**Przedmiot:** Programowanie Aplikacji Mobilnych  
**Środowisko:** Android Studio 2026.1 (Ladybug / Quail)  
**Minimalna wersja SDK:** `minSdk 27` (Android 8.1 Oreo)  
**Język:** Kotlin  

---

##  Opis projektu

Aplikacja jest mobilnym quizem wiedzy o historii systemu Android, rozwijanym w ramach dwóch etapów zadań laboratoryjnych.

### Zadanie 1: Podstawy UI i obsługa pytań (Quiz)
* Wykorzystanie architektury **ViewBinding** (`ActivityMainBinding`) do bezpiecznego dostępu do elementów widoku.
* Wyświetlanie 5 pytań typu Prawda/Fałsz zrobionych na zasobach napisowych (`strings.xml`).
* Zapobieganie wielokrotnemu odpowiadaniu na to samo pytanie (`already_answered`).
* Zliczanie wyniku i wyświetlanie dialogu podsumowującego (`AlertDialog`) po rozwiązaniu quizu.
* Resetowanie stanu quizu po zakończeniu lub przejściu przez wszystkie pytania.

### Zadanie 2: Cykl życia, zachowanie stanu i druga aktywność (PromptActivity & Intent)
* Logowanie zdarzeń cyklu życia aktywności w `Logcat` (`onStart`, `onResume`, `onPause`, `onStop`, `onRestart`, `onDestroy`).
* Zapis i odtwarzanie stanu aktywności przy zmianie konfiguracji (obrót ekranu) za pomocą `onSaveInstanceState`.
* Obsługa drugiej aktywności `PromptActivity` służącej do podglądu podpowiedzi:
  * Przekazywanie poprawnej odpowiedzi z `MainActivity` do `PromptActivity` przez `Intent`.
  * Zwracanie wyniku (`answerWasShown`) z `PromptActivity` do `MainActivity` za pomocą `registerForActivityResult`.
  * Brak punktu za pytanie, jeśli użytkownik skorzystał z podpowiedzi (`answer_was_shown`).
* Dokumentacja logów cyklu życia zebrana w katalogu `docs/cykl-zycia.md`.

---

## 🛠️ Jak uruchomić projekt

1. **W Android Studio:**
   * Otwórz katalog projektu w Android Studio.
   * Poczekaj na zakończenie synchronizacji Gradle.
   * Wybierz podłączone urządzenie (emulator lub fizyczny telefon z min. Android 8.1 / API 27).
   * Kliknij **Run** (Ctrl+R / Shift+F10).

2. **Z wiersza poleceń (Gradle Wrapper):**
   * Budowanie wersji debugującej:
     ```bash
     ./gradlew assembleDebug
     ```
     *(w systemie Windows: `gradlew.bat assembleDebug`)*

---

## 📂 Struktura katalogów

* `app/src/main/java/com/example/zadanie1/` – kod źródłowy w Kotlinie (`MainActivity.kt`, `PromptActivity.kt`, `Question.kt`).
* `app/src/main/res/` – układy widoków (`activity_main.xml`, `activity_prompt.xml`) oraz zasoby tekstów i kolorów.
* `docs/cykl-zycia.md` – dokumentacja logów cyklu życia aktywności zgodnie z wymaganiami instrukcji.
