<?php
header("Content-Type: application/json");

// Leer datos del body
$data = json_decode(file_get_contents("php://input"));

$nombre = trim($data->nombre ?? '');
$email = trim($data->email ?? '');
$pass = trim($data->password ?? '');

// Validación básica
if (!$nombre || !$email || !$pass) {
    http_response_code(400);
    echo json_encode(["success" => false, "message" => "Todos los campos son obligatorios."]);
    exit;
}

// Conexión a MySQL
$host = "localhost";
$user = "back";
$password = "za8G$6k72";
$database = "tu_base_de_datos";

$conn = new mysqli($host, $user, $password, $database);

if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["success" => false, "message" => "Error en la conexión con la base de datos."]);
    exit;
}

// Verificar si el email ya está registrado
$stmt = $conn->prepare("SELECT id FROM usuarios WHERE email = ?");
$stmt->bind_param("s", $email);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    http_response_code(409);
    echo json_encode(["success" => false, "message" => "El correo ya está registrado."]);
    $stmt->close();
    $conn->close();
    exit;
}
$stmt->close();

// Insertar nuevo usuario
$hash = password_hash($pass, PASSWORD_DEFAULT);
$stmt = $conn->prepare("INSERT INTO usuarios (nombre, email, password) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $nombre, $email, $hash);

if ($stmt->execute()) {
    echo json_encode(["success" => true, "message" => "Registro exitoso."]);
} else {
    http_response_code(500);
    echo json_encode(["success" => false, "message" => "Error al registrar usuario."]);
}

$stmt->close();
$conn->close();
?>
