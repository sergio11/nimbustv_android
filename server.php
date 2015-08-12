<?php

$apiKey = 'AIzaSyAYqWa_spBkHWJWEALQfBN8zH_W58XQkgo';
 
// Cabecera
$headers = array('Content-Type:application/json',
                 "Authorization:key=$apiKey");
 
// Datos
$payload = array('mensaje' => utf8_encode('Nueva oferta'),
                 'id' => '16044');
$registrationIdsArray = array('APA91bFZUX68MM2cKOv9novB-9dzBr0GDCFOPCzEHy4klGxd7OfKtWvnTPREgbMNtO-3SdWfJLEBTuCkraN6Lr1aww8z69YVyQ4c1f4VsCJO1DJfbgf8ShwpF-aBGLDp0K8oaAmcSkFQxDslg27cQJtBmsP9ZDFCSvNYF963aXVpmqrYZqdNbVE');
 
$data = array(
   'data' => $payload,
   'registration_ids' => $registrationIdsArray
);
 
// Petición
$ch = curl_init();
curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers );
curl_setopt( $ch, CURLOPT_URL, "https://android.googleapis.com/gcm/send" );
curl_setopt( $ch, CURLOPT_SSL_VERIFYHOST, 0 );
curl_setopt( $ch, CURLOPT_SSL_VERIFYPEER, 0 );
curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode($data));
 
// Conectamos y recuperamos la respuesta
$response = curl_exec($ch);

echo "RESPUESTA: " . PHP_EOL;
print_r($response);
 
// Cerramos conexión
curl_close($ch);