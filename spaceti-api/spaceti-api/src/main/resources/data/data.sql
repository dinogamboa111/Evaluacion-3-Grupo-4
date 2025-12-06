INSERT INTO producto (nombre, descripcion, precio, imagen_url, stock, categoria) 
SELECT * FROM (
    SELECT 
        'Laptop Dell XPS 15' as nombre,
        'Laptop de alto rendimiento con procesador Intel Core i7 de 11va generación, 16GB RAM DDR4, SSD 512GB NVMe' as descripcion,
        1299.99 as precio,
        'https://i.dell.com/is/image/DellContent/content/dam/ss2/product-images/dell-client-products/notebooks/xps-notebooks/xps-15-9520/media-gallery/notebook-xps-15-9520-nt-blue-gallery-4.psd' as imagen_url,
        10 as stock,
        'Computadores' as categoria
    UNION ALL
    SELECT 'Mouse Logitech MX Master 3', 'Mouse ergonómico inalámbrico con sensor de alta precisión 4000 DPI', 99.99, 'https://resource.logitechg.com/w_692,c_lpad,ar_4:3,q_auto,f_auto,dpr_1.0/d_transparent.gif/content/dam/gaming/en/products/mx-master-3s/gallery/mx-master-3s-mouse-top-view-graphite.png', 50, 'Periféricos'
    UNION ALL
    SELECT 'Teclado Mecánico Keychron K2', 'Teclado mecánico inalámbrico 75% con switches Gateron Brown y retroiluminación RGB', 89.99, 'https://cdn.shopify.com/s/files/1/0059/0630/1017/files/Keychron-K2-wireless-mechanical-keyboard-for-Mac-Windows-iOS-Gateron-switch-red-with-type-C-RGB-white-backlight-aluminum-frame_1800x1800.jpg', 30, 'Periféricos'
    UNION ALL
    SELECT 'Monitor LG UltraWide 34"', 'Monitor curvo ultrawide 34 pulgadas, resolución QHD 3440x1440, 144Hz', 599.99, 'https://www.lg.com/content/dam/channel/wcms/cl/images/monitores/34wn80c-b_awh_eail_cl_c/DZ-14.jpg', 15, 'Monitores'
    UNION ALL
    SELECT 'Webcam Logitech C920', 'Cámara web Full HD 1080p a 30fps con micrófono estéreo integrado', 79.99, 'https://resource.logitech.com/w_692,c_lpad,ar_4:3,q_auto,f_auto,dpr_1.0/d_transparent.gif/content/dam/logitech/en/products/webcams/c920/gallery/c920-gallery-1.png', 25, 'Periféricos'
    UNION ALL
    SELECT 'Auriculares Sony WH-1000XM4', 'Auriculares inalámbricos over-ear con cancelación activa de ruido', 349.99, 'https://www.sony.cl/image/5d02da5df552836db0ac37fb4aef11ec?fmt=pjpeg&wid=1014&hei=396', 20, 'Audio'
    UNION ALL
    SELECT 'SSD Samsung 970 EVO Plus 1TB', 'Unidad de estado sólido M.2 NVMe Gen 3.0 x4, velocidades hasta 3500 MB/s', 129.99, 'https://images.samsung.com/is/image/samsung/p6pim/cl/mz-v7s1t0bw/gallery/cl-970-evo-plus-nvme-m-2-ssd-mz-v7s1t0bw-368339081', 40, 'Almacenamiento'
    UNION ALL
    SELECT 'Router ASUS RT-AX88U', 'Router gaming WiFi 6 AX6000 de doble banda con 8 puertos Gigabit LAN', 299.99, 'https://dlcdnwebimgs.asus.com/gain/69C1137B-CFAB-4920-8473-6F3F95D1C97F/w717/h525', 12, 'Redes'
    UNION ALL
    SELECT 'Impresora HP LaserJet Pro', 'Impresora láser monocromática con WiFi y impresión dúplex automática', 199.99, 'https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c06373477.png', 8, 'Impresoras'
    UNION ALL
    SELECT 'Tablet iPad Air 2024', 'iPad Air con chip M1, pantalla Liquid Retina de 10.9 pulgadas, 256GB', 699.99, 'https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/ipad-air-finish-select-gallery-202211-blue-wifi', 18, 'Tablets'
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM producto LIMIT 1);