function Gauss() {
	let ready = false;
	let second = 0.0;

	
	this.next = function(mean, min, max) {
		mean = mean == undefined ? 0.0 : mean;
		let dev = 1.0;

		
		if (this.ready) {
			this.ready = false;
			if(this.second * dev + mean <= min){
				return min;
			}
			if(this.second * dev + mean >= max){
				return max;
			}
			return this.second * dev + mean;
		}
		else {
			let u, v, s;
			do {
				u = 2.0 * Math.random() - 1.0;
				v = 2.0 * Math.random() - 1.0;
				s = u * u + v * v;
			} while (s > 1.0 || s == 0.0);
			
			var r = Math.sqrt(-2.0 * Math.log(s) / s);
			this.second = r * u;
			this.ready = true;
			if(r * v * dev + mean <= min){
				return min;
			}
			if(r * v * dev + mean >=max){
				return max;
			}
			return r * v * dev + mean;
		}
	};
}