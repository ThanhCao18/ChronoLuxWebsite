# Kiểm tra Java
$javaVersion = java -version 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "Lỗi: Java chưa được cài đặt hoặc chưa được thêm vào PATH"
    Write-Host "Vui lòng cài đặt Java và thêm vào PATH trước khi chạy script này"
    exit
}

# Kiểm tra plantuml.jar
if (-not (Test-Path "plantuml.jar")) {
    Write-Host "Đang tải plantuml.jar..."
    Invoke-WebRequest -Uri "https://github.com/plantuml/plantuml/releases/download/v1.2024.3/plantuml-1.2024.3.jar" -OutFile "plantuml.jar"
}

# Tạo thư mục images nếu chưa tồn tại
$imagesDir = "docs/design/sequence-diagrams/images"
if (-not (Test-Path $imagesDir)) {
    New-Item -ItemType Directory -Path $imagesDir -Force
}

# Xuất các diagram sang PNG
Get-ChildItem "docs/design/sequence-diagrams/*.puml" | ForEach-Object {
    $filename = $_.Name
    Write-Host "Đang xuất $filename..."
    java -jar plantuml.jar -tpng $_.FullName -o $imagesDir
}

Write-Host "Hoàn thành xuất các diagram!"
Write-Host "Các file PNG đã được lưu trong thư mục: $imagesDir" 