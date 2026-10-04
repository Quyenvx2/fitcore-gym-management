Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PWD\backend'; .\mvnw.cmd spring-boot:run"

Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PWD\frontend'; npm.cmd run dev"