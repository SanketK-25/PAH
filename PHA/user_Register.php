<?php

    class user_Register{
        private $conn;
        public $responce = array();
        function __construct()
        {
            require_once dirname(__FILE__).'/connection.php';
            $db = new connect_DB();
            $this->conn = $db->connect();
        }

        function isUser($username){
            $stmt = $this->conn->prepare("SELECT user_name,user_pass FROM `users` WHERE user_name = ?");
            $stmt->bind_param('s',$username);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows() > 0;
        }
        
        function register($username, $userpass, $name, $contact, $address){
            if($this->isUser($username)){
                
                $responce['error'] = "'Username is already in use'";
                return $responce;
            }else{
                $stmt = $this->conn->prepare("INSERT INTO `users` (`user_name`, `user_pass`, `u_name`, `u_contact`, `u_address`) 
                    VALUES (?, ?, ?, ?, ?)");
                $stmt->bind_param("sssss",$username, $userpass, $name, $contact, $address);
                if($stmt->execute()){
                    $responce['error'] = "false";
                }else{
                    $responce['error'] = "Server didnt reach";
                }
                return $responce;
            }
        }
    }

// $db = new user_Register;
// $result = $db->register('Kaushik1', '4321', 'Kaushik D Walwadkar', '1234567891', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
// echo $result['error'];