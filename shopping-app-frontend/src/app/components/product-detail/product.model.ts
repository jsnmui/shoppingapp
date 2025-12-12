 export interface Product {
  productId: number;
  name: string;
  description: string;
  retailPrice: number;

   // Optional fields (for admin)
  wholesalePrice?: number;
  quantity?: number;
}