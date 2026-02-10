package agalfioni.recipesai.recipe.data.utils

val fakeAIJsonRawResponse = """
generateRecipes: raw [
  {
    "title": "Lemon Herb Roasted Chicken & Potatoes",
    "difficulty": "easy",
    "minutes_time": 45,
    "ingredient_coverage": 40,
    "confidence": 0.5,
    "instructions": [
      "Preheat oven to 200°C (400°F).",
      "Cut potatoes into 2-inch chunks and chicken breasts into similar size pieces if desired, or leave whole.",
      "In a large bowl, toss potatoes and chicken with olive oil, lemon juice, dried oregano, salt, and black pepper.",
      "Spread the mixture in a single layer on a baking sheet.",
      "Roast for 30-35 minutes, or until chicken is cooked through and potatoes are tender and golden brown, flipping halfway.",
      "Serve hot, garnished with fresh parsley if desired."
    ],
    "nutrition": {
      "calories": 550
    },
    "ingredients": [
      {
        "name": "Chicken Breast",
        "quantity": 500,
        "unit": "gram"
      },
      {
        "name": "Potatoes",
        "quantity": 400,
        "unit": "gram"
      },
      {
        "name": "Olive Oil",
        "quantity": 2,
        "unit": "tablespoon"
      },
      {
        "name": "Lemon Juice",
        "quantity": 1,
        "unit": "tablespoon"
      },
      {
        "name": "Dried Oregano",
        "quantity": 1,
        "unit": "teaspoon"
      },
      {
        "name": "Salt",
        "quantity": 0.5,
        "unit": "teaspoon"
      },
      {
        "name": "Black Pepper",
        "quantity": 0.25,
        "unit": "teaspoon"
      },
      {
        "name": "Fresh Parsley",
        "quantity": 1,
        "unit": "tablespoon"
      }
    ]
  },
  {
    "title": "Cheesy Carrot Fritters",
    "difficulty": "moderate",
    "minutes_time": 35,
    "ingredient_coverage": 40,
    "confidence": 0.5,
    "instructions": [
      "Grate carrots and cheese into a large bowl.",
      "Add flour, egg, finely chopped onion, salt, and black pepper. Mix well until combined.",
      "Heat olive oil in a large non-stick skillet over medium heat.",
      "Spoon tablespoonfuls of the mixture into the skillet, flattening slightly to form fritters.",
      "Cook for 3-4 minutes per side, or until golden brown and cooked through.",
      "Serve hot with a dollop of sour cream or yogurt dip."
    ],
    "nutrition": {
      "calories": 300
    },
    "ingredients": [
      {
        "name": "Carrots",
        "quantity": 3,
        "unit": "medium"
      },
      {
        "name": "Cheddar Cheese",
        "quantity": 100,
        "unit": "gram"
      },
      {
        "name": "All-Purpose Flour",
        "quantity": 0.25,
        "unit": "cup"
      },
      {
        "name": "Egg",
        "quantity": 1,
        "unit": "piece"
      },
      {
        "name": "Yellow Onion",
        "quantity": 0.25,
        "unit": "small"
      },
      {
        "name": "Salt",
        "quantity": 0.5,
        "unit": "teaspoon"
      },
      {
        "name": "Black Pepper",
        "quantity": 0.25,
        "unit": "teaspoon"
      },
      {
        "name": "Olive Oil",
        "quantity": 3,
        "unit": "tablespoon"
      }
    ]
  },
  {
    "title": "Creamy Tomato & Chicken Pasta",
    "difficulty": "easy",
    "minutes_time": 30,
    "ingredient_coverage": 40,
    "confidence": 0.5,
    "instructions": [
      "Cook pasta according to package directions until al dente. Drain and set aside.",
      "While pasta cooks, dice chicken breast into 1-inch pieces. Season with salt and pepper.",
      "Heat olive oil in a large skillet over medium-high heat. Add chicken and cook until browned and cooked through, about 5-7 minutes. Remove chicken from skillet and set aside.",
      "Add minced garlic and chopped onion to the skillet. Sauté until softened, about 3 minutes.",
      "Stir in canned crushed tomatoes, heavy cream, and dried basil. Bring to a simmer.",
      "Return cooked chicken to the skillet. Add the drained pasta and toss to coat. Cook for an additional 2-3 minutes, allowing sauce to thicken slightly.",
      "Serve hot, garnished with fresh basil or Parmesan cheese."
    ],
    "nutrition": {
      "calories": 600
    },
    "ingredients": [
      {
        "name": "Pasta",
        "quantity": 250,
        "unit": "gram"
      },
      {
        "name": "Chicken Breast",
        "quantity": 300,
        "unit": "gram"
      },
      {
        "name": "Canned Crushed Tomatoes",
        "quantity": 400,
        "unit": "gram"
      },
      {
        "name": "Heavy Cream",
        "quantity": 100,
        "unit": "ml"
      },
      {
        "name": "Garlic",
        "quantity": 2,
        "unit": "clove"
      },
      {
        "name": "Yellow Onion",
        "quantity": 0.5,
        "unit": "medium"
      },
      {
        "name": "Olive Oil",
        "quantity": 1,
        "unit": "tablespoon"
      },
      {
        "name": "Dried Basil",
        "quantity": 1,
        "unit": "teaspoon"
      },
      {
        "name": "Salt",
        "quantity": 0.5,
        "unit": "teaspoon"
      },
      {
        "name": "Black Pepper",
        "quantity": 0.25,
        "unit": "teaspoon"
      }
    ]
  },
  {
    "title": "Rustic Potato & Carrot Mash with Cheese",
    "difficulty": "easy",
    "minutes_time": 30,
    "ingredient_coverage": 60,
    "confidence": 0.7,
    "instructions": [
      "Peel and chop potatoes and carrots into 1-inch pieces.",
      "Place potatoes and carrots in a large pot, cover with cold water, and add a pinch of salt.",
      "Bring to a boil, then reduce heat and simmer for 15-20 minutes, or until vegetables are very tender.",
      "Drain the vegetables thoroughly.",
      "Return the drained vegetables to the hot pot. Add butter, milk, and grated cheese.",
      "Mash with a potato masher until desired consistency is reached (some lumps are fine for a rustic mash).",
      "Season with salt and black pepper to taste. Serve hot."
    ],
    "nutrition": {
      "calories": 350
    },
    "ingredients": [
      {
        "name": "Potatoes",
        "quantity": 500,
        "unit": "gram"
      },
      {
        "name": "Carrots",
        "quantity": 200,
        "unit": "gram"
      },
      {
        "name": "Cheddar Cheese",
        "quantity": 80,
        "unit": "gram"
      },
      {
        "name": "Butter",
        "quantity": 2,
        "unit": "tablespoon"
      },
      {
        "name": "Milk",
        "quantity": 60,
        "unit": "ml"
      },
      {
        "name": "Salt",
        "quantity": 0.5,
        "unit": "teaspoon"
      },
      {
        "name": "Black Pepper",
        "quantity": 0.25,
        "unit": "teaspoon"
      }
    ]
  },
  {
    "title": "Chicken, Potato & Tomato Stew",
    "difficulty": "moderate",
    "minutes_time": 60,
    "ingredient_coverage": 60,
    "confidence": 0.7,
    "instructions": [
      "Cut chicken thighs into 1.5-inch pieces. Season with salt and pepper.",
      "Heat olive oil in a large Dutch oven or pot over medium-high heat. Brown chicken on all sides, then remove and set aside.",
      "Add chopped onion and minced garlic to the pot. Sauté until softened, about 5 minutes.",
      "Stir in chopped potatoes and diced tomatoes (canned or fresh).",
      "Pour in chicken broth and add dried thyme. Bring to a simmer.",
      "Return chicken to the pot. Reduce heat to low, cover, and simmer for 30-40 minutes, or until chicken is tender and potatoes are cooked through.",
      "Season with additional salt and pepper if needed. Serve hot, garnished with fresh parsley."
    ],
    "nutrition": {
      "calories": 480
    },
    "ingredients": [
      {
        "name": "Chicken Thighs",
        "quantity": 600,
        "unit": "gram"
      },
      {
        "name": "Potatoes",
        "quantity": 300,
        "unit": "gram"
      },
      {
        "name": "Canned Diced Tomatoes",
        "quantity": 400,
        "unit": "gram"
      },
      {
        "name": "Yellow Onion",
        "quantity": 1,
        "unit": "medium"
      },
      {
        "name": "Garlic",
        "quantity": 2,
        "unit": "clove"
      },
      {
        "name": "Chicken Broth",
        "quantity": 500,
        "unit": "ml"
      },
      {
        "name": "Olive Oil",
        "quantity": 1,
        "unit": "tablespoon"
      },
      {
        "name": "Dried Thyme",
        "quantity": 1,
        "unit": "teaspoon"
      },
      {
        "name": "Salt",
        "quantity": 0.5,
        "unit": "teaspoon"
      },
      {
        "name": "Black Pepper",
        "quantity": 0.25,
        "unit": "teaspoon"
      },
      {
        "name": "Fresh Parsley",
        "quantity": 1,
        "unit": "tablespoon"
      }
    ]
  },
  {
    "title": "Baked Chicken Breast with Tomato and Cheese",
    "difficulty": "easy",
    "minutes_time": 35,
    "ingredient_coverage": 60,
    "confidence": 0.7,
    "instructions": [
      "Preheat oven to 200°C (400°F).",
      "Place chicken breasts in a baking dish. Season with salt, black pepper, and dried Italian seasoning.",
      "Slice tomatoes and place them on top of the chicken breasts.",
      "Sprinkle grated mozzarella cheese generously over the tomatoes and chicken.",
      "Bake for 20-25 minutes, or until chicken is cooked through (internal temperature of 74°C/165°F) and cheese is melted and bubbly.",
      "Serve hot with a side salad or steamed vegetables."
    ],
    "nutrition": {
      "calories": 450
    },
    "ingredients": [
      {
        "name": "Chicken Breast",
        "quantity": 2,
        "unit": "piece"
      },
      {
        "name": "Tomatoes",
        "quantity": 2,
        "unit": "medium"
      },
      {
        "name": "Mozzarella Cheese",
        "quantity": 100,
        "unit": "gram"
      },
      {
        "name": "Dried Italian Seasoning",
        "quantity": 1,
        "unit": "teaspoon"
      },
      {
        "name": "Salt",
        "quantity": 0.5,
        "unit": "teaspoon"
      },
      {
        "name": "Black Pepper",
        "quantity": 0.25,
        "unit": "teaspoon"
      },
      {
        "name": "Olive Oil",
        "quantity": 1,
        "unit": "tablespoon"
      }
    ]
  },
  {
    "title": "Loaded Potato Skins with Cheese",
    "difficulty": "moderate",
    "minutes_time": 50,
    "ingredient_coverage": 40,
    "confidence": 0.5,
    "instructions": [
      "Preheat oven to 200°C (400°F).",
      "Scrub potatoes clean. Pierce them several times with a fork. Bake directly on the oven rack for 45-60 minutes, or until very tender.",
      "Once cooled enough to handle, cut each potato in half lengthwise. Carefully scoop out most of the flesh, leaving about 0.5 cm (1/4 inch) attached to the skin.",
      "Brush the potato skins lightly with olive oil. Sprinkle with salt.",
      "Bake the empty skins for 10-15 minutes, until crispy.",
      "Fill the crispy skins with the scooped-out potato flesh (optional: mash with a little butter and milk first), grated cheddar cheese, and cooked bacon bits if using.",
      "Return to the oven for 5-7 minutes, or until cheese is melted and bubbly.",
      "Garnish with chopped chives and serve with sour cream."
    ],
    "nutrition": {
      "calories": 400
    },
    "ingredients": [
      {
        "name": "Potatoes",
        "quantity": 4,
        "unit": "medium"
      },
      {
        "name": "Cheddar Cheese",
        "quantity": 150,
        "unit": "gram"
      },
      {
        "name": "Olive Oil",
        "quantity": 1,
        "unit": "tablespoon"
      },
      {
        "name": "Salt",
        "quantity": 0.5,
        "unit": "teaspoon"
      },
      {
        "name": "Bacon Bits",
        "quantity": 30,
        "unit": "gram"
      },
      {
        "name": "Sour Cream",
        "quantity": 60,
        "unit": "gram"
      },
      {
        "name": "Fresh Chives",
        "quantity": 1,
        "unit": "tablespoon"
      }
    ]
  },
  {
    "title": "Chicken & Vegetable Skewers",
    "difficulty": "moderate",
    "minutes_time": 40,
    "ingredient_coverage": 60,
    "confidence": 0.7,
    "instructions": [
      "If using wooden skewers, soak them in water for at least 30 minutes to prevent burning.",
      "Cut chicken breast into 1-inch cubes. Cut carrots into thick rounds or half-moons, cherry tomatoes in half, and bell peppers and onion into 1-inch pieces.",
      "In a bowl, combine chicken and vegetables with olive oil, soy sauce, honey, minced garlic, salt, and black pepper. Toss to coat.",
      "Thread the chicken and vegetables alternately onto the skewers.",
      "Preheat grill to medium-high heat. Grill skewers for 15-20 minutes, turning occasionally, until chicken is cooked through and vegetables are tender-crisp.",
      "Alternatively, bake in a preheated oven at 200°C (400°F) for 20-25 minutes, flipping halfway."
    ],
    "nutrition": {
      "calories": 380
    },
    "ingredients": [
      {
        "name": "Chicken Breast",
        "quantity": 400,
        "unit": "gram"
      },
      {
        "name": "Carrots",
        "quantity": 2,
        "unit": "medium"
      },
      {
        "name": "Cherry Tomatoes",
        "quantity": 150,
        "unit": "gram"
      },
      {
        "name": "Green Bell Pepper",
        "quantity": 1,
        "unit": "medium"
      },
      {
        "name": "Red Onion",
        "quantity": 0.5,
        "unit": "medium"
      },
      {
        "name": "Olive Oil",
        "quantity": 2,
        "unit": "tablespoon"
      },
      {
        "name": "Soy Sauce",
        "quantity": 1,
        "unit": "tablespoon"
      },
      {
        "name": "Honey",
        "quantity": 1,
        "unit": "tablespoon"
      },
      {
        "name": "Garlic",
        "quantity": 2,
        "unit": "clove"
      },
      {
        "name": "Salt",
        "quantity": 0.5,
        "unit": "teaspoon"
      },
      {
        "name": "Black Pepper",
        "quantity": 0.25,
        "unit": "teaspoon"
      }
    ]
  },
  {
    "title": "Cheesy Potato & Carrot Gratin",
    "difficulty": "elaborated",
    "minutes_time": 75,
    "ingredient_coverage": 60,
    "confidence": 0.7,
    "instructions": [
      "Preheat oven to 190°C (375°F). Butter a 9x13 inch baking dish.",
      "Peel potatoes and carrots. Slice them very thinly using a mandoline or a sharp knife.",
      "In a large bowl, combine sliced potatoes and carrots with heavy cream, minced garlic, nutmeg, salt, and black pepper. Toss gently to ensure all slices are coated.",
      "Arrange half of the potato and carrot slices in an even layer in the prepared baking dish.",
      "Sprinkle with half of the grated Gruyere cheese.",
      "Layer the remaining potato and carrot slices on top, then sprinkle with the remaining cheese.",
      "Cover the baking dish tightly with aluminum foil.",
      "Bake for 45 minutes. Remove foil and bake for an additional 15-20 minutes, or until vegetables are tender and the top is golden brown and bubbly.",
      "Let stand for 10 minutes before serving."
    ],
    "nutrition": {
      "calories": 420
    },
    "ingredients": [
      {
        "name": "Potatoes",
        "quantity": 700,
        "unit": "gram"
      },
      {
        "name": "Carrots",
        "quantity": 300,
        "unit": "gram"
      },
      {
        "name": "Gruyere Cheese",
        "quantity": 150,
        "unit": "gram"
      },
      {
        "name": "Heavy Cream",
        "quantity": 300,
        "unit": "ml"
      },
      {
        "name": "Garlic",
        "quantity": 2,
        "unit": "clove"
      },
      {
        "name": "Nutmeg",
        "quantity": 0.25,
        "unit": "teaspoon"
      },
      {
        "name": "Salt",
        "quantity": 0.75,
        "unit": "teaspoon"
      },
      {
        "name": "Black Pepper",
        "quantity": 0.5,
        "unit": "teaspoon"
      },
      {
        "name": "Butter",
        "quantity": 1,
        "unit": "tablespoon"
      }
    ]
  },
  {
    "title": "One-Pan Roasted Chicken & Root Vegetables",
    "difficulty": "easy",
    "minutes_time": 50,
    "ingredient_coverage": 80,
    "confidence": 0.9,
    "instructions": [
      "Preheat oven to 200°C (400°F).",
      "Cut chicken thighs into 1.5-inch pieces. Chop potatoes, carrots, and onion into similar size chunks. Halve cherry tomatoes.",
      "In a large bowl, combine chicken, potatoes, carrots, onion, and cherry tomatoes.",
      "Drizzle with olive oil and season generously with dried rosemary, salt, and black pepper. Toss well to coat all ingredients evenly.",
      "Spread the mixture in a single layer on a large baking sheet.",
      "Roast for 35-40 minutes, or until chicken is cooked through (internal temperature of 74°C/165°F) and vegetables are tender and slightly caramelized, stirring once halfway through.",
      "Serve directly from the pan for an easy meal."
    ],
    "nutrition": {
      "calories": 520
    },
    "ingredients": [
      {
        "name": "Chicken Thighs",
        "quantity": 600,
        "unit": "gram"
      },
      {
        "name": "Potatoes",
        "quantity": 300,
        "unit": "gram"
      },
      {
        "name": "Carrots",
        "quantity": 200,
        "unit": "gram"
      },
      {
        "name": "Cherry Tomatoes",
        "quantity": 150,
        "unit": "gram"
      },
      {
        "name": "Yellow Onion",
        "quantity": 1,
        "unit": "medium"
      },
      {
        "name": "Olive Oil",
        "quantity": 2,
        "unit": "tablespoon"
      },
      {
        "name": "Dried Rosemary",
        "quantity": 1,
        "unit": "teaspoon"
      },
      {
        "name": "Salt",
        "quantity": 0.75,
        "unit": "teaspoon"
      },
      {
        "name": "Black Pepper",
        "quantity": 0.5,
        "unit": "teaspoon"
      }
    ]
  }
]
""".trimIndent()