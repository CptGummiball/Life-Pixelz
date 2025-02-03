# LifePixelz Simulation

LifePixelz ist eine einfache Simulation, die das Verhalten von Pixeln in einem Gitter mit Ressourcen und Lagern nachbildet. Die Pixel interagieren mit Ressourcen, bauen Gebäude und kämpfen miteinander. Diese Simulation nutzt JavaFX, um die Interaktionen visuell darzustellen.

**Hinweis:** Dieses Projekt ist ein **Work in Progress (WIP)**. Es gibt noch viele Verbesserungsmöglichkeiten und bekannte Probleme, die behoben werden müssen.

## Features

- **Pixel**: Kleine Einheiten, die auf dem Gitter leben, sich bewegen, Ressourcen abbauen, Gebäude errichten und miteinander kämpfen.
- **Ressourcen**: Abbaubare Felder, die sich mit der Zeit regenerieren, wenn sie verbraucht werden.
- **Gebäude (Lager)**: Pixel können an bestimmten Stellen Gebäude errichten, um Ressourcen zu speichern.
- **Kampf**: Pixel können miteinander kämpfen und dabei Energie verlieren.
- **Log**: Alle Aktionen der Pixel werden in einem Log angezeigt, das den Zustand der Simulation verfolgt.
- **Regenerierung der Ressourcen**: Ressourcen erneuern sich mit einer Wahrscheinlichkeit und bleiben nach dem Abbau weiterhin im Gitter.

## Funktionsweise

1. Beim Starten des Programms wird ein Gitter angezeigt, auf dem Pixel leben.
2. Die Pixel bewegen sich zufällig und interagieren mit Ressourcen, Lagern und anderen Pixeln.
3. Das Log-Fenster zeigt alle wichtigen Aktionen der Pixel an, wie das Abbauen von Ressourcen, den Bau von Lagern und den Kampf.
4. Ressourcen regenerieren sich mit der Zeit, aber nur, wenn sie abgebaut wurden, bleiben sie im Gitter und können wiederverwendet werden.

## Architektur

### Klassen:

1. **LifePixelz (Main-Klasse)**: Startet die Anwendung und verwaltet die Simulation.
2. **Grid**: Repräsentiert das Gitter, das die Zellen enthält, in denen Pixel leben. Es enthält Methoden zur Ressourcengenerierung und -erneuerung.
3. **Cell**: Repräsentiert eine einzelne Zelle im Gitter, die entweder leer, eine Ressource oder ein Gebäude sein kann.
4. **Pixel**: Repräsentiert ein Pixel, das sich über das Gitter bewegt, Ressourcen abbaut, Kämpfe ausführt und Gebäude errichtet.

### Funktionsweise:

- **Zellen (Cell)** können drei Zustände haben:
  - **empty**: Eine leere Zelle, auf der ein Pixel ein Gebäude errichten kann.
  - **resource**: Eine Ressource, die abgebaut werden kann.
  - **building**: Ein Gebäude (Lager), das von Pixeln genutzt wird, um Ressourcen zu speichern.
  
- **Simulation**: Die Simulation läuft in einem eigenen Thread, der in regelmäßigen Abständen das Gitter aktualisiert, Pixel bewegt und deren Aktionen ausführt.

## Beispielansicht

![LifePixelz Screenshot](https://github.com/CptGummiball/Life-Pixelz/blob/main/screenshot.PNG)

> Ein Screenshot der LifePixelz Simulation (wenn du das Repository auf deinem Rechner ausführst, wirst du die Ansicht sehen können).

## Mitwirken

Wenn du Verbesserungsvorschläge hast oder zur Weiterentwicklung beitragen möchtest, bist du herzlich eingeladen, eine Pull-Anfrage zu stellen!

## Lizenz

Dieses Projekt ist unter der MIT-Lizenz lizenziert - siehe [LICENSE](LICENSE) für Details.
