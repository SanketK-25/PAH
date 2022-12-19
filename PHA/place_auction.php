<?php

    class place_auction{   
        private $conn;
        private $responce = array();
        function __construct()
        {
            require_once dirname(__FILE__).'/connection.php';
            $db = new connect_DB();
            $this->conn = $db->connect();
        }
            
        function put_auction($user_id, $item_id, $duration, $base_price)
        {
            if($this->isAuctionExist($user_id, $item_id, $duration, $base_price)){
                $this->responce['error'] = true;
                $this->responce['msg'] = "The auction for this item is already open";
            }else{
                mysqli_query($this->conn,"INSERT INTO `place_auction` (`a_id`, `u_id`, `item_id`, `deadline`, `base_price`, `winner_id`, `status`) 
                                            VALUES (NULL, '{$user_id}', '{$item_id}', current_timestamp()+ INTERVAL {$duration} SECOND, '{$base_price}', NULL, 'open');");
                $a_id = $this->conn->insert_id;
                mysqli_query($this->conn,"CREATE EVENT evet".$item_id." ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL {$duration} SECOND ON COMPLETION NOT PRESERVE
                                            DO update place_auction set status = 'close' where u_id = {$user_id} and item_id = {$item_id} ;");
                
                require_once dirname(__FILE__).'/winner_op.php';

                $da = new winner_op();
                $da->win($item_id,$duration,$a_id);

                $this->responce['error'] = false;
                $this->responce['msg'] = "The auction placed successfuly";
            }
            return $this->responce;
            
        }

        function isAuctionExist($user_id, $item_id){
            $stmt = $this->conn->prepare("SELECT * FROM `place_auction` WHERE u_id = ? and item_id = ? and status='open'");
            $stmt->bind_param('ss',$user_id,$item_id);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows() > 0;
        }
        
    }


