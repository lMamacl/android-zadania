# Dokumentacja cyklu życia aktywności - Zadanie 2

**Aplikacja:** Zadanie 1 (Quiz)  
**Komponent:** `MainActivity`  
**Filtr w Logcat:** `package:mine level:debug tag:MainActivity`  
**Wersja systemu Android:** Android 14 (API 34)  

---

## Tabela scenariuszy i logów cyklu życia

| Scenariusz | Logi z Logcata (`MainActivity`) | Wyjaśnienie powiązanych metod cyklu życia |
| :--- | :--- | :--- |
| **1. Pierwsze uruchomienie aplikacji (Cold launch)** | `Wywołanie metody onCreate`<br>`Wywołanie metody onStart`<br>`Wywołanie metody onResume` | `onCreate` inicjalizuje widoki i stan aktywności. `onStart` sprawia, że aktywność staje się widoczna dla użytkownika. `onResume` wprowadza aktywność w stan interaktywny (gotowy na odbieranie zdarzeń). |
| **2. Otwarcie podpowiedzi (Przejście do `PromptActivity`)** | `Wywołanie metody onPause`<br>`Wywołanie metody onStop` | `onPause` jest wywoływane, gdy aktywność traci fokus na rzecz nowej aktywności (`PromptActivity`). Gdy nowa aktywność całkowicie przysłania `MainActivity`, następuje `onStop`. |
| **3. Powrót z podpowiedzi do quizu** | `Wywołanie metody onRestart`<br>`Wywołanie metody onStart`<br>`Wywołanie metody onResume`<br>`answer was shown: true` | `onRestart` przygotowuje aktywność powracającą z tła, po czym `onStart` i `onResume` ponownie przywracają jej widoczność i interaktywność. Callback `registerForActivityResult` odbiera wynik z `PromptActivity`. |
| **4. Naciśnięcie przycisku Home / Przejście do listy ostatnich aplikacji** | `Wywołanie metody onPause`<br>`Wywołanie metody onStop`<br>`Wywołanie metody onSaveInstanceState, current question X` | Aktywność przechodzi w tło (`onPause` -> `onStop`). Na systemie Android 14 metoda `onSaveInstanceState` wywoływana jest po `onStop`, aby utrwalić stan instancji przed ewentualnym usunięciem procesu z pamięci. |
| **5. Powrót z tła (z listy ostatnich aplikacji)** | `Wywołanie metody onRestart`<br>`Wywołanie metody onStart`<br>`Wywołanie metody onResume` | Proces aplikacji nie został zabity, więc aktywność jest wznawiana z pamięci RAM bez powtórnego wywoływania `onCreate`. |
| **6. Obrót ekranu (zmiana konfiguracji)** | `Wywołanie metody onPause`<br>`Wywołanie metody onStop`<br>`Wywołanie metody onSaveInstanceState, current question X`<br>`Wywołanie metody onDestroy`<br>`Wywołanie metody onCreate`<br>`Wywołanie metody onStart`<br>`Wywołanie metody onResume` | Zmiana orientacji ekranu niszczy starą instancję aktywności (`onDestroy`) i tworzy nową (`onCreate`). Stan gry (`currentQuestionIndex`, `correctAnswersCount`, `currentQuestionAnswered`, `answerWasShown`) zostaje zachowany dzięki `onSaveInstanceState` i odtworzony w `onCreate`. |
| **7. Przycisk Cofnij (Back gesture / button) z głównej aktywności** | `Wywołanie metody onPause`<br>`Wywołanie metody onStop`<br>`Wywołanie metody onSaveInstanceState, current question X` | **Uwaga Android 14:** Od systemu Android 12/14 naciśnięcie przycisku Back na aktywnej głównej aktywności przenosi aplikację do tła (zachowanie analogiczne do Home) bez natychmiastowego wywoływania `onDestroy`. |

---

## Test zapisu stanu (zliczanie i zmiana orientacji)

1. Przejdź do **3. pytania** w quizie.
2. Zaznacz odpowiedź lub otwórz podpowiedź.
3. Obróć ekran w emulatorze/telefonie (zmiana orientacji).
4. **Wynik:** Pytanie pozostaje na 3. pozycji, zapamiętane są udzielone odpowiedzi oraz flaga skorzystania z podpowiedzi, a wynik końcowy zlicza się prawidłowo po przejściu całego quizu.
