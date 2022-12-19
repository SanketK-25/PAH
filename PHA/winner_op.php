<?php

    class winner_op{
        private $conn;
        function __construct()
        {
            require_once dirname(__FILE__).'/connection.php';
            $db = new connect_DB();
            $this->conn = $db->connect();
        }

        function win($item_id,$duration,$a_id){
            $r = mysqli_query($this->conn,"CREATE EVENT winevet".$item_id." ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL {$duration} SECOND ON COMPLETION NOT PRESERVE
            DO UPDATE place_auction set winner_id = (SELECT u_id from place_bid where a_id = {$a_id} having max(bid_price)) where a_id = {$a_id}   ;");
            $res = $r;//->fetch_assoc();
            echo json_encode( $res);
        }


    }

