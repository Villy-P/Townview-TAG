.ONESHELL:

craps-reset:
	rmdir craps/build
	mkdir craps/build
	cd craps/build
	cmake -GNinja ..

craps-run:
	ninja -C craps/build
	.\craps\build\Craps.exe