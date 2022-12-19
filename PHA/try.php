<?php
require_once dirname(__FILE__).'/connection.php';
$db = new connect_DB();
$conn = $db->connect();
$result = mysqli_query($conn,"CREATE EVENT winevet ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 30 SECOND ON COMPLETION NOT PRESERVE
DO UPDATE place_auction set winner_id = (SELECT u_id from place_bid where a_id = {$_POST['a_id']} and bid_price = (SELECT max(bid_price) FROM place_bid) )
where a_id = {$_POST['a_id']} ;");
$r = $result;//->fetch_assoc();
echo json_encode( $r);




