//创建地图
init()
var map, fs=new Array() ,vL;
function init() {
	console.log("test");
    map = new ol.Map({
        target: 'mapP',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM(),

            })
        ],
        view: new ol.View({
            center: ol.proj.transform([37.41, 8.82], 'EPSG:4326', 'EPSG:3857'),
            zoom: 4
        }),
        controls : ol.control.defaults().extend(
            [ new ol.control.ScaleLine() ]),
        logo : false
    });
}