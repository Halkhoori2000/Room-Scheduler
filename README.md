# Room Scheduler

A room reservation system originally built as a Java Swing desktop application with Apache Derby DB (CMPSC 221, Penn State — Summer 2021), rebuilt as a fully interactive browser app using HTML, CSS, and JavaScript with localStorage.

**[Live Demo](https://halkhoori2000.github.io/Room-Scheduler/)**

---

## Overview

The system allows administrators to manage faculty, rooms, dates, and reservations. It implements automatic room assignment using a best-fit algorithm and a FIFO waitlist that promotes entries when rooms become available or are cancelled.

## Features

- Add faculty members, rooms (with seat capacity), and available dates
- Reserve a room for a faculty member on a specific date — smallest fitting room is automatically assigned
- Waitlist: if no room fits, the reservation is queued and promoted automatically when capacity frees up
- Cancel reservations — waitlisted entries are promoted immediately
- Delete rooms — active reservations are reassigned or waitlisted, waitlisted entries may be promoted
- Filter reservations by faculty, date, room, and status
- All data persisted in `localStorage` — no backend required

## Tech Stack

| Layer | Original | Web Version |
|---|---|---|
| UI | Java Swing (NetBeans GroupLayout) | HTML / CSS |
| Storage | Apache Derby (embedded SQL DB) | localStorage |
| Logic | Java services + DAOs | Vanilla JavaScript |
| Platform | Desktop (JVM) | Browser |

## Project Structure

```
Room-Scheduler/
├── index.html      # Complete browser app (live demo)
└── src/            # Original Java Swing source code
    ├── RoomScheduler.java
    ├── constant/
    ├── dao/
    ├── dto/
    ├── model/
    ├── service/
    ├── utils/
    └── component/
```

## Running the Web Version

Open `index.html` directly in any modern browser — no server or build step needed.

## Original Java App

The `src/` folder contains the full Java source. Built with NetBeans IDE using:
- **Java Swing** — GroupLayout for pixel-accurate UI
- **Apache Derby** — embedded relational database for persistence
- **DAO / Service pattern** — clean separation of data access and business logic

To run the original app, open the project in NetBeans with Apache Derby configured and run `RoomScheduler.java`.
