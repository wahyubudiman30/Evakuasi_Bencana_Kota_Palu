<?php 

require_once('connection.php');

    $oldpass = $_POST['oldpassword'];
    $newpass = $_POST['newpassword'];
    $conpass = $_POST['conpassword'];
    $email = $_POST['email'];
    
    $sql = "SELECT * FROM akun WHERE password = '$oldpass' AND email = '$email';
    $query = mysqli_query($conn,$sql);

if ($newpass == $conpass) {

    if (!mysqli_num_rows($query) > 0){
        echo "Password lama tidak cocok";
    }
    else{
        $update = "UPDATE akun SET password = '$newpass' where email = '$email';
        $res = mysqli_query($conn,$update);
        if ($res){
            echo "Ubah password berhasil!";
        }
        else{
            echo "Error!"
        }

    }
}else{
    echo "Password tidak sama";
}

?>