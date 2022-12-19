<?php

    class user_Login{
        private $conn;

        function __construct()
        {
            require_once dirname(__FILE__).'/connection.php';
            $db = new connect_DB();
            $this->conn = $db->connect();
            
        }

        function login($username,$userpass){
                $stmt = $this->conn->prepare("SELECT * FROM `users` WHERE user_name = ? and user_pass = ?");
                $stmt->bind_param("ss",$username,$userpass);
                $stmt->execute();
                return $stmt->get_result()->fetch_assoc();    
        }

    }


