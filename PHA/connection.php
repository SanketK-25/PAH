<?php

    class connect_DB{

        private $con;

        function connect(){
            include_once dirname(__FILE__).'/constants.php';

            $this->con = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);

            mysqli_query($this->con,"SET GLOBAL event_scheduler='ON'");

            if(mysqli_connect_error()){
                echo "Error while connection";
            }
            
            
            return $this->con;

        }
    }


    
