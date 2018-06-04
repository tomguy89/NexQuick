select * from qpPosition
where substr(bCode, 0, 3)='116'
order by abs(to_number('1165053100')-to_number(bCode)),
abs(power(qplatitude-37.5037617, 2)+power(QPLONGITUDE-127.0241388, 2));