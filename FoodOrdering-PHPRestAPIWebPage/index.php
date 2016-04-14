<?php
include("db-connection.php");
include("FoodHelper.php");
?>

<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Food List</title>
        <link href="img/icon-food.png" type="image/gif" rel="shortcut icon" />
        <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
        <link href="css/bootstrap-theme.min.css" type="text/css" rel="stylesheet" />
        <link href="css/style.css" type="text/css" rel="stylesheet" />
    </head>
    <body>
        <header class="navbar navbar-static-top header-section" id="top" role="banner">
            <div class="container" id="header">
                <div class="navbar-header">
                    <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#header-navbar" aria-controls="header-navbar" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a href="#" class="navbar-brand"><img src="img/icon-food.png" /></a>
                </div>
                <nav id="header-navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="#orderedList">Ordered List</a></li>
                        <li><a href="#finishedList">Finished List</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#footer">Contact</a></li>
                    </ul>
                </nav>
            </div>
        </header>
        <main class="content-head" role="main" tabindex="-1">
            <div class="container">
                <h2 class="features-title" id="orderedList">Ordered List</h2>
                <?php
                $foodUtil = new FoodHelper();
                $tables = $foodUtil->findOrderedTable($db, 0);
                if (count($tables) > 0) {
                    for ($i = 0; $i < count($tables); $i++) {
                        $tb = $tables[$i];
                        ?>
                        <div class="row">
                            <div class="col-md-12">
                                <h3 class="table-title">Table <?= $tb["tablenumber"] ?> (<?= $tb["orderdate"] ?>)</h3>
                                <?php
                                $orderedFoods = $foodUtil->findOrderedFoods($db, $tb["tablenumber"], 0);
                                foreach ($orderedFoods as $food) {
                                    ?>
                                    <div class="col-md-4 col-sm-4 col-sx-12">
                                        <img src="img/food/<?= $food["foodimage"] ?>" alt="<?= $food["foodname"] ?>" class="img-responsive" />
                                        <h3> <?= $food["foodname"] ?></h3>
                                        <p> Note: <?= $food["note"] ?> - <button class="btn-primary" id="<?= $food["orderdetailid"] ?>">Finish</button> </p>
                                    </div>
                                    <?php
                                }
                                ?>
                            </div>
                        </div>
                        <?php
                    }
                } else {
                    ?>
                    <div class="col-sm-12">
                        <h3> There is no orders at the moment! </h3>
                    </div>
                    <?php
                }
                ?>
                <p>&nbsp;</p>
                <h2 class="features-title" id="finishedList">Finished List</h2>
                <?php
                $finishedTables = $foodUtil->findOrderedTable($db, 1);
                if (count($finishedTables) > 0) {
                    for ($i = 0; $i < count($finishedTables); $i++) {
                        $ftb = $finishedTables[$i];
                        ?>
                        <div class="row">
                            <div class="col-md-12">
                                <h3 class="table-title">Table <?= $ftb["tablenumber"] ?> (<?= $ftb["orderdate"] ?>)</h3>
                                <?php
                                $finishedFoods = $foodUtil->findOrderedFoods($db, $ftb["tablenumber"], 1);
                                foreach ($finishedFoods as $food) {
                                    ?>
                                    <div class="col-md-4 col-sm-4 col-sx-12">
                                        <img src="img/food/<?= $food["foodimage"] ?>" alt="<?= $food["foodname"] ?>" class="img-responsive" />
                                        <h3> <?= $food["foodname"] ?></h3>
                                        <p> Note: <?= $food["note"] ?></p>
                                    </div>
                                    <?php
                                }
                                ?>
                            </div>
                        </div>
                        <?php
                    }
                } else {
                    ?>
                    <div class="col-sm-12">
                        <h3> There is no items here! </h3>
                    </div>
                    <?php
                }
                ?>
            </div>
        </main>
        <footer class="footer-section" role="contentinfo">
            <div class="container" id="footer">
                <h2 class="features-title">Contact</h2>
                <div class="row">
                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <img src="img/icon-email.gif" alt="Email Icon" class="img-responsive icon" />
                        <span>android@mum.edu</span>
                    </div>
                    <div class="col-md-3 col-sm-3 col-xs-6">
                        <a href="tel:012-345-6789" title="Make a phone call">
                            <img src="img/icon-telephone.gif" alt="Telephone Icon" class="img-responsive icon" />
                            <span>(+1)(012)-345-6789</span>
                        </a>
                    </div>
                </div>
            </div>
        </footer>
    </body>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <script src="js/jQuery.lightweightScrollTo.js" type="text/javascript"></script>
    <script src="js/main.js" type="text/javascript"></script>
</html>