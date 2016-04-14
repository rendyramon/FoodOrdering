<?php

class FoodHelper {

    public function __construct() {
    }

    public function findOrderedTable($db, $status) {
        $tables = array();
        try {
            $stmt = $db->prepare("SELECT DISTINCT od.tablenumber, (SELECT MAX(DATE) FROM orderdetail tb WHERE tb.tablenumber = od.tablenumber) AS orderdate FROM orderdetail od WHERE od.status = :status ORDER BY od.date");
            $stmt->bindValue(":status", $status, PDO::PARAM_INT);
            $stmt->execute();
            foreach ($stmt as $row) {
                $tables[] = $row;
            }
        } catch (PDOException $ex) {
            return array();
        }
        return $tables;
    }

    public function findOrderedFoods($db, $table, $status) {
        $foodList = array();
        try {
            $stmt = $db->prepare("SELECT od.orderdetailid, f.foodname, f.foodimage, f.fooddescription, od.note, od.tablenumber, od.date FROM orderdetail od, food f WHERE od.foodid = f.foodid AND od.status = :status AND od.tablenumber = :table ORDER BY f.foodname");
            $stmt->execute(array(":table" => $table, ":status" => $status));
            foreach ($stmt as $row) {
                $foodList[] = $row;
            }
        } catch (PDOException $ex) {
            return array();
        }
        return $foodList;
    }

    public function findFinishedFoods($db) {
        $foodList = array();
        try {
            $stmt = $db->prepare("SELECT od.tablenumber, od.orderdetailid, f.foodname, f.foodimage, f.fooddescription, od.note, od.date FROM orderdetail od, food f WHERE od.foodid = f.foodid AND od.status = 1 ORDER BY od.date DESC");
            $stmt->execute();
            foreach ($stmt as $row) {
                $foodList[] = $row;
            }
        } catch (PDOException $ex) {
            return array();
        }
        return $foodList;
    }

    public function updateOrderDetailStatus($db, $id) {
        try {
            $stmt = $db->prepare("UPDATE orderdetail od SET od.status = 1 WHERE od.orderdetailid = :input");
            $stmt->execute(array(":input" => $id));
            return TRUE;
        } catch (PDOException $ex) {
            return FALSE;
        }
    }

    public function insertOrderDetail($db, $foodId, $tableNo, $foodNote) {
        try {
            $stmt = $db->prepare("INSERT INTO orderdetail(foodid,note,tablenumber,status) VALUES (:foodid, :note, :tablenumber, 0)");
            $stmt->execute(array(":foodid" => $foodId, ":note" => $foodNote, ":tablenumber" => $tableNo));
            return TRUE;
        } catch (PDOException $ex) {
            return FALSE;
        }
    }

}
