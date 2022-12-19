<?php

    class place_bid{
        private $conn;
        private $responce;
        function __construct()
        {
            require_once dirname(__FILE__).'/connection.php';
            $db = new connect_DB();
            $this->conn = $db->connect();
        }

        function bid($user_id, $a_id, $bid_price){
            if(!$this->isBidPresent($user_id, $a_id)){
                mysqli_query($this->conn,"INSERT INTO `place_bid` (`b_id`, `u_id`, `a_id`, `bid_price`) 
                                            VALUES (NULL, '{$user_id}', '{$a_id}', '{$bid_price}');");
                $this->responce['error']=false;
                $this->responce['msg']="Query run successfuly";
            }else{
                $this->responce['error']=true;
                $this->responce['msg']="Bid Already present";
            }
            return $this->responce;
        }

        function isBidPresent($user_id, $a_id){
            $stmt = $this->conn->prepare("SELECT * FROM `place_bid` WHERE u_id = ? and a_id = ?");
            $stmt->bind_param('ss',$user_id,$a_id);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows() > 0;
        }
    }
