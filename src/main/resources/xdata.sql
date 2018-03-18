-- use igt;
INSERT INTO `user` (`id`,`cdate`,`mdate`,`email`, `name`, `password`, `username`, `created_by`, `modified_by`) VALUES (1,'2017-11-22 14:52:58','2017-11-22 14:52:58','ruicunh@gmail.com', 'Rui Cunha', '$2a$04$DX7IEMhHcDHTOV6.O1xdzeM/IwN27527PQKBmpJTepQ8nmOyBADze', 'rui', '0', '0');

INSERT INTO `product` (`id`, `cdate`, `mdate`, `description`, `image`, `name`, `created_by`, `modified_by`) 
VALUES ('1', '2017-11-22 14:52:58', '2017-11-22 14:52:58', 'Desc', 'https://www.bbcgoodfood.com/sites/default/files/styles/carousel_small/public/recipe/recipe-image/2017/11/orange-marmalade-glazed-roast-duck.jpg?itok=yxM4LNJi', 'Rice', '1', '1'),
('2', '2017-11-22 14:52:58', '2017-11-22 14:52:58', 'Desc', 'https://www.bbcgoodfood.com/sites/default/files/styles/carousel_small/public/recipe/recipe-image/2017/11/orange-marmalade-glazed-roast-duck.jpg?itok=yxM4LNJi', 'Bannanas', '1', '1'),
('3', '2017-11-22 14:52:58', '2017-11-22 14:52:58', 'Desc', 'https://www.bbcgoodfood.com/sites/default/files/styles/carousel_small/public/recipe/recipe-image/2017/11/orange-marmalade-glazed-roast-duck.jpg?itok=yxM4LNJi', 'Carrots', '1', '1');

INSERT INTO `user_product` (`product_id`, `user_id`, `quantity`, `cdate`, `mdate`) 
VALUES ('1', '1', '4', '2017-11-22 14:52:58', '2017-11-22 14:52:58'),
('3', '1', '4', '2017-11-22 14:52:58', '2017-11-22 14:52:58'),
('2', '1', '4', '2017-11-22 14:52:58', '2017-11-22 14:52:58');
