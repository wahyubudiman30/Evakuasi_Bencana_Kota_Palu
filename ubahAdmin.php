<?php 
if($_SERVER['REQUEST_METHOD']=='POST'){
    //MEndapatkan Nilai Dari Variable 
    $id = $_POST['id'];
    $username = $_POST['username'];
    $email = $_POST['email'];
    $ttl = $_POST['ttl'];
    $jk = $_POST['jk'];
    $telepon = $_POST['telepon'];
    $jabatan = $_POST['jabatan'];
    $alamat = $_POST['alamat'];
    
    //import file koneksi database 
    require_once('connection.php');
    
    //Membuat SQL Query
    $sql = "UPDATE akun SET username = '$username', email = '$email', ttl = '$ttl', jk = '$jk', telepon = '$telepon', jabatan = '$jabatan', alamat = '$alamat' WHERE id = $id;";
    
    //Meng-update Database 
    if(mysqli_query($con,$sql)){
        echo 'Berhasil Update Data Admin';
    }else{
        echo 'Gagal Update Data Admin';
    }
    
    mysqli_close($con);
}