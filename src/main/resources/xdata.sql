-- Select * from user;
INSERT INTO `user` (`id`,`cdate`,`mdate`,`email`, `name`, `nutrition_type`, `password`, `username`, `created_by`, `modified_by`) VALUES (1,'2017-11-22 14:52:58','2017-11-22 14:52:58','ruicunh@gmail.com', 'Rui Cunha', 'all', '$2a$04$DX7IEMhHcDHTOV6.O1xdzeM/IwN27527PQKBmpJTepQ8nmOyBADze', 'rui', '0', '0');

INSERT INTO `product` (`id`, `cdate`, `mdate`, `calories`, `carbs`, `description`, `image`, `name`, `polyunsaturated_fat`, `proteins`, `saturated_fat`, `trans_fat`, `unsaturated_fat`, `vitamins`, `created_by`, `modified_by`) VALUES ('1', '2017-11-22 14:52:58', '2017-11-22 14:52:58', '1', '2', 'Desc', 'https://www.bbcgoodfood.com/sites/default/files/styles/carousel_small/public/recipe/recipe-image/2017/11/orange-marmalade-glazed-roast-duck.jpg?itok=yxM4LNJi', 'Rice', '3', '4', '5', '6', '7', '8', '1', '1');

INSERT INTO `igt`.`user_product` (`product_id`, `user_id`, `quantity`, `cdate`, `mdate`) VALUES ('1', '1', '4', '2017-11-22 14:52:58', '2017-11-22 14:52:58');
