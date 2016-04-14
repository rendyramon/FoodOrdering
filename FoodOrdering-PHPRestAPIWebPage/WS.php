<?php
include("db-connection.php");
include("FoodHelper.php");
?>
<?php

if (is_ajax()) {
    processFinishButtonClick($db);
} else {
    if (isset($_GET['method']) && !empty($_GET['method'])) {
        processGetFinishedListWS($db);
    } else {
        processAddFoodWS($db);
    }
}

function is_ajax() {
    return isset($_SERVER['HTTP_X_REQUESTED_WITH']) && strtolower($_SERVER['HTTP_X_REQUESTED_WITH']) == 'xmlhttprequest';
}

function processFinishButtonClick($db) {
    if (isset($_POST["action"]) && !empty($_POST["action"])) {
        $action = $_POST["action"];
        switch ($action) {
            case "finish":
                if (isset($_POST["input"]) && !empty($_POST["input"])) {
                    $input = $_POST["input"];
                    $foodUtil = new FoodHelper();
                    $result = $foodUtil->updateOrderDetailStatus($db, $input);
                    $data = array();
                    $data["result"] = "$result";
                    header('Content-Type: application/json');
                    echo json_encode($data);
                }
                break;
        }
    }
}

function processGetFinishedListWS($db) {
    $response['status'] = 200;
    $response['method'] = "finish";
    $foodUtil = new FoodHelper();
    $orderedFoods = $foodUtil->findFinishedFoods($db);
    foreach ($orderedFoods as $food) {
        
    }
    $response['data'] = $orderedFoods;
    deliver_response('json', $response);
}

function processAddFoodWS($db) {
    $response['status'] = 200;
    $response['method'] = "add";
    $tableNo = 0;
    $foodId = 0;
    $foodNote = "";
    if (isset($_GET['tableNo']) && !empty($_GET['tableNo'])) {
        $tableNo = intval($_GET['tableNo']);
    }
    if (isset($_GET['foodId']) && !empty($_GET['foodId'])) {
        $foodId = intval($_GET['foodId']);
    }
    if (isset($_GET['foodNote']) && !empty($_GET['foodNote'])) {
        $foodNote = $_GET['foodNote'];
    }
    if ($tableNo > 0 && $foodId > 0) {
        $foodUtil = new FoodHelper();
        $response['result'] = $foodUtil->insertOrderDetail($db, $foodId, $tableNo, $foodNote);
    }
    deliver_response('json', $response);
}

function deliver_response($format, $api_response) {
    $http_response_code = array(
        200 => 'OK',
        400 => 'Bad Request',
        401 => 'Unauthorized',
        403 => 'Forbidden',
        404 => 'Not Found'
    );
    header('HTTP/1.1 ' . $api_response['status'] . ' ' . $http_response_code[$api_response['status']]);
    if (strcasecmp($format, 'json') == 0) {
        if (strcasecmp($api_response['method'], 'finish') == 0) {
            header('Content-Type: application/json; charset=utf-8');
            $json_response = json_encode($api_response);
            echo $json_response;
        } else {
            header('Content-Type: application/json; charset=utf-8');
            $json_response = json_encode($api_response);
            echo $json_response;
        }
    } else {
        header('Content-Type: text/html; charset=utf-8');
        echo $api_response['data'];
    }
    exit;
}

?>